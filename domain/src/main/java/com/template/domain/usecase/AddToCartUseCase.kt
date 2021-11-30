package com.template.domain.usecase

import com.template.domain.repository.CartRepository
import com.template.models.GoodModel

class AddToCartUseCase(private val cartRepository: CartRepository) {
    fun execute(goodItem: GoodModel) {
        cartRepository.addGoodToCart(goodItem)
    }

}