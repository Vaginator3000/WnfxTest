package com.template.domain.usecase

import com.template.domain.repository.CartRepository
import com.template.models.GoodModelCart

class AddToCartUseCase(private val cartRepository: CartRepository) {
    fun execute(goodItem: GoodModelCart) {
        cartRepository.addGoodToCart(goodItem)
    }

}