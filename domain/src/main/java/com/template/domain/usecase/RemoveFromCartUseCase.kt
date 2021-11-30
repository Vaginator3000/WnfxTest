package com.template.domain.usecase

import com.template.domain.repository.CartRepository
import com.template.models.GoodModel

class RemoveFromCartUseCase(private val cartRepository: CartRepository) {
    fun execute(goodItem: GoodModel) {
        cartRepository.getGoodsInCart().remove(goodItem)
    }

}