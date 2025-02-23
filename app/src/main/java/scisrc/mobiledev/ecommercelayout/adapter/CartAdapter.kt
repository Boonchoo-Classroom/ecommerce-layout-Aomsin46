package scisrc.mobiledev.ecommercelayout.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import scisrc.mobiledev.ecommercelayout.databinding.ItemCartBinding
import scisrc.mobiledev.ecommercelayout.model.Product

class CartAdapter(
    private var cartItems: MutableList<Product>,
    private val onItemRemoved: (Product) -> Unit
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    class CartViewHolder(val binding: ItemCartBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val product = cartItems[position]
        holder.binding.productName.text = product.name
        holder.binding.productPrice.text = "${product.price} บาท"
        holder.binding.productImage.setImageResource(product.image)

        holder.binding.removeButton.setOnClickListener {
            onItemRemoved(product)
        }
    }

    override fun getItemCount() = cartItems.size

    fun updateList(newList: List<Product>) {
        cartItems.clear()
        cartItems.addAll(newList)
        notifyDataSetChanged()
    }
}