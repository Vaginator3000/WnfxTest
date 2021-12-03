package com.template.domain.repository

import com.template.models.GoodModelCart

interface CartRepository {
    fun addGoodToCart(goodItem: GoodModelCart)
    fun removeGoodFromCart(goodItem: GoodModelCart)
    fun getGoodsInCart() : List<GoodModelCart>?
    fun incGoodInCart(goodItem: GoodModelCart)
    fun redGoodInCart(goodItem: GoodModelCart)
}