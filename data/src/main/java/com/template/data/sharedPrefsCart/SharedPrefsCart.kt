package com.template.data.sharedPrefsCart

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.template.models.GoodModelCart

class SharedPrefsCart(context: Context)  {
    private val sPrefs = context.getSharedPreferences("mysettings",Context.MODE_PRIVATE)
   // private val sPrefs = (context as Activity).getPreferences(Context.MODE_PRIVATE)
    private val gson = Gson()

    fun getGoodsInCart(): List<GoodModelCart>? {
        val listInStr = sPrefs.getString("LIST", null) ?: return null

        val type = object : TypeToken<List<GoodModelCart>>() {}.type
        val goods: List<GoodModelCart> = gson.fromJson(listInStr, type)
        return  goods as MutableList<GoodModelCart>
    }

    fun saveGoodsToCart(goods: List<GoodModelCart>) {
        sPrefs.edit()
            .putString("LIST", gson.toJson(goods))
            .apply()
    }
}