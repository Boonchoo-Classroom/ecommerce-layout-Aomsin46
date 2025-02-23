package scisrc.mobiledev.ecommercelayout.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import scisrc.mobiledev.ecommercelayout.databinding.ItemProductBinding
import scisrc.mobiledev.ecommercelayout.model.Product
import scisrc.mobiledev.ecommercelayout.ui.ProductDetailActivity
import scisrc.mobiledev.ecommercelayout.utils.FavoriteManager
import scisrc.mobiledev.ecommercelayout.R

class ProductAdapter(
    private val context: Context,
    private var productList: List<Product>
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    class ProductViewHolder(val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = productList[position]
        holder.binding.productName.text = product.name
        holder.binding.productPrice.text = "${product.price} บาท"
        holder.binding.productImage.setImageResource(product.image)

        val isFavorite = FavoriteManager.getFavorites(context).contains(product)
        holder.binding.favoriteButton.setImageResource(
            if (isFavorite) R.drawable.love else R.drawable.love2
        )

        holder.binding.favoriteButton.setOnClickListener {
            if (isFavorite) {
                FavoriteManager.removeFromFavorites(context, product)
            } else {
                FavoriteManager.addToFavorites(context, product)
            }
            notifyItemChanged(position)
        }

        holder.itemView.setOnClickListener {
            val intent = Intent(it.context, ProductDetailActivity::class.java)
            intent.putExtra("product", product)
            it.context.startActivity(intent)
        }
    }

    override fun getItemCount() = productList.size

    fun updateList(newList: List<Product>) {
        productList = newList
        notifyDataSetChanged()
    }
}