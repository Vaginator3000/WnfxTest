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

    override fun incGoodInCart(goodItem: GoodModelCart) {
        sharedPrefsCart.incGoodInCart(goodToIncrease = goodItem)
    }

    override fun redGoodInCart(goodItem: GoodModelCart) {
        sharedPrefsCart.redGoodInCart(goodToReduce = goodItem)
    }

    override fun addGoodToCart(goodItem: GoodModelCart) {
        val goodsList =
            (sharedPrefsCart.getGoodsInCart() ?: mutableListOf()) as MutableList<GoodModelCart>

        if (goodsList.map{ it.good }.contains(goodItem.good)) {
            val index = goodsList.map{ it.good }.indexOf(goodItem.good)

            val newItem = GoodModelCart(
                good = goodItem.good,
                amount = goodsList[index].amount + 1
            )
            goodsList.removeAt(index)
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