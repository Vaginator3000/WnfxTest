package com.template.domain.usecase

import com.template.domain.repository.GoodsRepository
import com.template.models.GoodModel

class GetGoodsUseCase(private val goodsRepository: GoodsRepository) {

    suspend fun execute(): List<GoodModel> {
        return goodsRepository.getGoods()
    }
}