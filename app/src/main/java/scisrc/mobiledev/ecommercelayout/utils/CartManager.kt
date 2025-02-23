package scisrc.mobiledev.ecommercelayout.utils

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import scisrc.mobiledev.ecommercelayout.model.Product

object CartManager {
    private const val PREF_NAME = "EcommercePrefs"
    private const val CART_KEY = "cart_items"

    fun addToCart(context: Context, product: Product) {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        val cartItems = getCart(context).toMutableList()
        cartItems.add(product)

        val json = Gson().toJson(cartItems)
        editor.putString(CART_KEY, json)
        editor.apply()
    }

    fun getCart(context: Context): List<Product> {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val json = sharedPreferences.getString(CART_KEY, null) ?: return emptyList()
        val type = object : TypeToken<List<Product>>() {}.type
        return Gson().fromJson(json, type)
    }

    fun removeFromCart(context: Context, product: Product) {
        val cartItems = getCart(context).toMutableList()
        cartItems.remove(product)

        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val json = Gson().toJson(cartItems)
        editor.putString(CART_KEY, json)
        editor.apply()
    }

    fun clearCart(context: Context) {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        sharedPreferences.edit().remove(CART_KEY).apply()
    }
}