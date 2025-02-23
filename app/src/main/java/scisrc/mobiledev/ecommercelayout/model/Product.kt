package scisrc.mobiledev.ecommercelayout.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(
    val name: String,
    val price: Double,
    val description: String,
    val image: Int,
    val category: String,
    var quantity: Int = 1
) : Parcelable