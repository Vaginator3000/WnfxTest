package com.template.domain.usecase

import com.template.domain.repository.CartRepository
import com.template.models.GoodModelCart

class RedGoodInCartUseCase(private val cartRepository: CartRepository) {
    fun execute(goodToReduce: GoodModelCart) {
        cartRepository.redGoodInCart(goodToReduce)
    }
}