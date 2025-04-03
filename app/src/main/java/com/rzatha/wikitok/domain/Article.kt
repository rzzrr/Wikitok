package com.rzatha.wikitok.domain

import com.google.gson.annotations.SerializedName
import com.rzatha.wikitok.data.PreviewArticleImageTitle

data class Article(
    val id: Int,
    val title: String,
    val fullUrl: String? = null,
    val extractText: String? = null,
    val imageTitle: List<PreviewArticleImageTitle>? = null,
    var imageUrl : String?  = null
)