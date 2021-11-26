package com.template.data.retrofit.common

import com.template.data.retrofit.RetrofitBuilder
import com.template.data.retrofit.services.ApiServices

object Common {
    private const val BASE_URL = "http://94.127.67.113:8099/"
    val apiServices: ApiServices
        get() = RetrofitBuilder.getClient(BASE_URL).create(ApiServices::class.java)
}