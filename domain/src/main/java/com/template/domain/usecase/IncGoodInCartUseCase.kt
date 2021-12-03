package com.template.domain.usecase

import com.template.domain.repository.CartRepository
import com.template.models.GoodModelCart

class IncGoodInCartUseCase(private val cartRepository: CartRepository) {
    fun execute(goodToIncrease: GoodModelCart) {
        cartRepository.incGoodInCart(goodToIncrease)
    }
}