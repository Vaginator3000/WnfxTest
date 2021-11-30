package com.template.domain.repository

import com.template.models.GoodModel

interface CartRepository {
    fun addGoodToCart(goodItem: GoodModel)
    fun removeGoodFromCart(goodItem: GoodModel)
}