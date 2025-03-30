package com.rzatha.wikitok.data.network.imageResponse

import com.google.gson.annotations.SerializedName

data class QueryImages(
    @SerializedName("pages") val imgPages: ImgPages
)
