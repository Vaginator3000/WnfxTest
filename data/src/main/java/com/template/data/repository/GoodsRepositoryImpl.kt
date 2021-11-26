package com.template.data.repository

import com.template.domain.repository.GoodsRepository
import com.template.models.GoodModel
import com.template.data.retrofit.common.Common


class GoodsRepositoryImpl : GoodsRepository {

    override suspend fun getGoods(): List<GoodModel> {

        return Common.apiServices.getGoodsList()
    }
}