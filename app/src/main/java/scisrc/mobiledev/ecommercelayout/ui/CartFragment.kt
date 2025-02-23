package scisrc.mobiledev.ecommercelayout.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import scisrc.mobiledev.ecommercelayout.adapter.CartAdapter
import scisrc.mobiledev.ecommercelayout.databinding.FragmentCartBinding
import scisrc.mobiledev.ecommercelayout.model.Product
import scisrc.mobiledev.ecommercelayout.utils.CartManager

class CartFragment : Fragment() {
    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!
    private lateinit var cartAdapter: CartAdapter
    private var cartItems = mutableListOf<Product>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartBinding.inflate(inflater, container, false)

        cartItems = CartManager.getCart(requireContext()).toMutableList()
        cartAdapter = CartAdapter(cartItems) { product ->
            removeItemFromCart(product)
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = cartAdapter

        updateTotalPrice()

        return binding.root
    }

    private fun removeItemFromCart(product: Product) {
        CartManager.removeFromCart(requireContext(), product)
        cartItems.remove(product)
        cartAdapter.updateList(cartItems)
        updateTotalPrice()
    }

    private fun updateTotalPrice() {
        val totalPrice = cartItems.sumOf { it.price }
        binding.totalPrice.text = "ราคารวม: $totalPrice บาท"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}