package com.template.domain.repository

import com.template.models.GoodModel


interface GoodsRepository {

    suspend fun getGoods(): List<GoodModel>

}