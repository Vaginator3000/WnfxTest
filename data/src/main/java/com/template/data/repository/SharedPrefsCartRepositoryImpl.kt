package com.template.data.repository

import android.app.Activity
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.template.data.sharedPrefsCart.SharedPrefsCart
import com.template.domain.repository.CartRepository
import com.template.models.GoodModel

class SharedPrefsCartRepositoryImpl(context: Context) : CartRepository {
    val sharedPrefsCart = SharedPrefsCart(context)

    override fun addGoodToCart(goodItem: GoodModel) {
        val goodsList = sharedPrefsCart.getGoodsIdsInCart() as MutableList<GoodModel>
        goodsList.add(goodItem)
        sharedPrefsCart.saveGoodsToCart(goodsList)

    }

    override fun removeGoodFromCart(goodItem: GoodModel) {
        val goodsList = sharedPrefsCart.getGoodsIdsInCart() as MutableList<GoodModel>
        goodsList.remove(goodItem)
        sharedPrefsCart.saveGoodsToCart(goodsList)
    }
}