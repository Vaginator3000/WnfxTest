package com.template.domain.usecase

import com.template.domain.repository.CartRepository
import com.template.models.GoodModelCart

class GetGoodsInCartUseCase(private val cartRepository: CartRepository) {
        fun execute() : List<GoodModelCart>? {
            return cartRepository.getGoodsInCart()
        }


}