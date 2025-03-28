package com.rzatha.wikitok.data

import com.google.gson.annotations.SerializedName

class ArticlePreviewDto(
    @SerializedName("pageid") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("extract") val extractText: String?,
    @SerializedName("images") val images: List<PreviewArticleImageTitle>?
) {
    var imageUrl : String? = null
}