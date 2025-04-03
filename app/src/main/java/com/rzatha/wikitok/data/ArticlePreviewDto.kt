package com.rzatha.wikitok.data

import com.google.gson.annotations.SerializedName

class ArticlePreviewDto(
    @SerializedName("pageid") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("fullurl") val fullUrl: String,
    @SerializedName("extract") val extractText: String?,
    @SerializedName("images") val images: List<PreviewArticleImageTitle>?,
    var imageUrl : String? = null
)