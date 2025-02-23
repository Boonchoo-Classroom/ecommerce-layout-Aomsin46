package scisrc.mobiledev.ecommercelayout.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import scisrc.mobiledev.ecommercelayout.databinding.ActivityProductDetailBinding
import scisrc.mobiledev.ecommercelayout.model.Product
import scisrc.mobiledev.ecommercelayout.utils.CartManager

class ProductDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProductDetailBinding
    private lateinit var product: Product

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        product = intent.getParcelableExtra("product")!!

        binding.productName.text = product.name
        binding.productPrice.text = "${product.price} บาท"
        Glide.with(this).load(product.image).into(binding.productImage)

        binding.backButton.setOnClickListener {
            finish()  // ปิด Activity นี้และกลับไปหน้าก่อนหน้า
        }


        binding.addToCartButton.setOnClickListener {
            CartManager.addToCart(this, product)
        }
    }
}