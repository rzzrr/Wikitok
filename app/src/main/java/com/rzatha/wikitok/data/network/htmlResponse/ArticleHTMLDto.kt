package com.rzatha.wikitok.data.network.htmlResponse

import com.google.gson.annotations.SerializedName

class ArticleHTMLDto(
    @SerializedName("pageid") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("text") val text: TextItem
) {
}