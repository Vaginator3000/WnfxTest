package com.template.domain.usecase

import com.template.models.GoodModel
import com.template.utils.SortBy

class SortGoodsUseCase(private val goodsList: List<GoodModel>) {
    fun execute(sortBy: SortBy) =
        when (sortBy) {
            SortBy.UP_PRICE -> goodsList.sortedBy { it.price }
            SortBy.DOWN_PRICE -> goodsList.sortedByDescending { it.price }
            SortBy.UP_RATING -> goodsList.sortedBy { it.rating }
            SortBy.DOWN_RATING -> goodsList.sortedByDescending { it.rating }
        }
}