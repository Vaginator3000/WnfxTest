package com.template.models

import com.google.gson.annotations.SerializedName


data class GoodModel(
    //image
    var name: String? = null,
    var shortInfo: String? = null,
    @SerializedName("desc")
    var fullInfo: String? = null,
    var price: Int? = null,
    @SerializedName("weight")
    var rating: Int? = null,
    @SerializedName("image")
    var imageurl: String? = null,
)