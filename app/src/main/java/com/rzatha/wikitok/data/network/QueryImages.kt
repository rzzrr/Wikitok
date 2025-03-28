package com.rzatha.wikitok.data.network

import com.google.gson.annotations.SerializedName
import com.rzatha.wikitok.data.ArticlePreviewDto

data class QueryImages(
    @SerializedName("pages") val imgPages: ImgPages
)
