package scisrc.mobiledev.ecommercelayout.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import scisrc.mobiledev.ecommercelayout.R
import scisrc.mobiledev.ecommercelayout.adapter.ProductAdapter
import scisrc.mobiledev.ecommercelayout.adapter.CategoryAdapter
import scisrc.mobiledev.ecommercelayout.databinding.FragmentAllProductsBinding
import scisrc.mobiledev.ecommercelayout.model.Product

class AllProductsFragment : Fragment() {
    private var _binding: FragmentAllProductsBinding? = null
    private val binding get() = _binding!!

    private val allProducts = listOf(
        Product("ปากกา", 10.0, "ปากกา", R.drawable.pen, "Pen"),
        Product("ดินสอ", 5.0, "ดินสอ", R.drawable.pensi, "Pen"),
        Product("ยางลบ", 25.0, "ยางลบ", R.drawable.pol, "pen"),
        Product("ไม้เบส", 150.0, "ไม้เบส", R.drawable.best, "ball"),
        Product("ลูกบอล", 200.0, "ลูกบอล", R.drawable.ball, "ball"),
        Product("ลูกเบสบอล", 500.0, "ลูกเบสบอล", R.drawable.bestball, "ball"),

    )

    private var filteredProducts = allProducts.toMutableList()
    private lateinit var productAdapter: ProductAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAllProductsBinding.inflate(inflater, container, false)


        productAdapter = ProductAdapter(requireContext(), filteredProducts)
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = productAdapter


        val categories = listOf("All") + allProducts.map { it.category }.distinct()
        val categoryAdapter = CategoryAdapter(categories) { selectedCategory ->
            filterProductsByCategory(selectedCategory)
        }
        binding.categoryRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.categoryRecyclerView.adapter = categoryAdapter

        return binding.root
    }


    private fun filterProductsByCategory(category: String) {
        filteredProducts = if (category == "All") {
            allProducts.toMutableList()
        } else {
            allProducts.filter { it.category == category }.toMutableList()
        }
        productAdapter.updateList(filteredProducts)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}