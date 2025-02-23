package scisrc.mobiledev.ecommercelayout.utils

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import scisrc.mobiledev.ecommercelayout.model.Product

object FavoriteManager {
    private const val PREF_NAME = "EcommercePrefs"
    private const val FAVORITE_KEY = "favorite_items"

    fun addToFavorites(context: Context, product: Product) {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        val favorites = getFavorites(context).toMutableList()
        if (!favorites.contains(product)) {
            favorites.add(product)
        }

        val json = Gson().toJson(favorites)
        editor.putString(FAVORITE_KEY, json)
        editor.apply()
    }

    fun removeFromFavorites(context: Context, product: Product) {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        val favorites = getFavorites(context).toMutableList()
        favorites.remove(product)

        val json = Gson().toJson(favorites)
        editor.putString(FAVORITE_KEY, json)
        editor.apply()
    }

    fun getFavorites(context: Context): List<Product> {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val json = sharedPreferences.getString(FAVORITE_KEY, null) ?: return emptyList()
        val type = object : TypeToken<List<Product>>() {}.type
        return Gson().fromJson(json, type)
    }
}