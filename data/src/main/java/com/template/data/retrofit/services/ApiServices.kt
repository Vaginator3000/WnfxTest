package com.template.data.retrofit.services

import com.template.models.GoodModel
import retrofit2.http.GET

interface ApiServices {
    @GET("getGoods")
    suspend fun getGoodsList(): List<GoodModel>
}