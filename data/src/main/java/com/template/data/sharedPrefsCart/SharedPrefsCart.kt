package com.template.data.sharedPrefsCart

import android.app.Activity
import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.template.domain.repository.CartRepository
import com.template.models.GoodModel

class SharedPrefsCart(context: Context)  {
    private val sPrefs = (context as Activity).getPreferences(Context.MODE_PRIVATE)
    private val gson = Gson()

    fun getGoodsIdsInCart(): MutableList<GoodModel>? {
        val listInStr = sPrefs.getString("LIST", null) ?: return null

        val type = object : TypeToken<List<GoodModel>>() {}.type
        val goods: List<GoodModel> = gson.fromJson(listInStr, type)
        return  goods as MutableList<GoodModel>
    }

    fun saveGoodsToCart(goods: List<GoodModel>) {
        sPrefs.edit()
            .putString("LIST", gson.toJson(goods))
            .apply()
    }
}