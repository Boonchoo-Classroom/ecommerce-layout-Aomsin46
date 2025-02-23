package scisrc.mobiledev.ecommercelayout.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import scisrc.mobiledev.ecommercelayout.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val PREF_NAME = "UserProfile"
    private val PICK_IMAGE_REQUEST = 1
    private var imageUri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)

        loadUserProfile()

        binding.editProfileButton.setOnClickListener {
            enableEditing(true)
        }

        binding.saveProfileButton.setOnClickListener {
            saveUserProfile()
            enableEditing(false)
        }

        binding.profileImage.setOnClickListener {
            pickImageFromGallery()
        }

        return binding.root
    }

    private fun enableEditing(enable: Boolean) {
        binding.nameInput.isEnabled = enable
        binding.surnameInput.isEnabled = enable
        binding.addressInput.isEnabled = enable
        binding.saveProfileButton.visibility = if (enable) View.VISIBLE else View.GONE
        binding.editProfileButton.visibility = if (enable) View.GONE else View.VISIBLE
    }

    private fun saveUserProfile() {
        val sharedPreferences = requireActivity().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        editor.putString("name", binding.nameInput.text.toString())
        editor.putString("surname", binding.surnameInput.text.toString())
        editor.putString("address", binding.addressInput.text.toString())
        editor.putString("imageUri", imageUri?.toString())

        editor.apply()
    }

    private fun loadUserProfile() {
        val sharedPreferences = requireActivity().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

        binding.nameInput.setText(sharedPreferences.getString("name", ""))
        binding.surnameInput.setText(sharedPreferences.getString("surname", ""))
        binding.addressInput.setText(sharedPreferences.getString("address", ""))

        val savedImageUri = sharedPreferences.getString("imageUri", null)
        if (savedImageUri != null) {
            binding.profileImage.setImageURI(Uri.parse(savedImageUri))
        }
    }

    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            imageUri = data.data
            binding.profileImage.setImageURI(imageUri)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}