package com.template.data.repository

import android.content.Context
import com.template.data.sharedPrefsCart.SharedPrefsCart
import com.template.domain.repository.CartRepository
import com.template.models.GoodModelCart

class SharedPrefsCartRepositoryImpl(context: Context) : CartRepository {
    private val sharedPrefsCart = SharedPrefsCart(context)

    override fun getGoodsInCart(): List<GoodModelCart>? {
        return sharedPrefsCart.getGoodsInCart()
    }

    override fun addGoodToCart(goodItem: GoodModelCart) {
        val goodsList =
            (sharedPrefsCart.getGoodsInCart() ?: mutableListOf()) as MutableList<GoodModelCart>

        if (goodsList.contains(goodItem)) {
            val newItem = GoodModelCart(
                good = goodItem.good,
                amount = goodItem.amount + 1
            )
            val index = goodsList.indexOf(goodItem)
            goodsList.remove(goodItem)
            goodsList.add(index, newItem)
        } else
            goodsList.add(goodItem)

        sharedPrefsCart.saveGoodsToCart(goodsList)

    }

    override fun removeGoodFromCart(goodItem: GoodModelCart) {
        sharedPrefsCart.getGoodsInCart() ?: return
        val goodsList = sharedPrefsCart.getGoodsInCart() as MutableList<GoodModelCart>
        goodsList.remove(goodItem)
        sharedPrefsCart.saveGoodsToCart(goodsList)
    }
}