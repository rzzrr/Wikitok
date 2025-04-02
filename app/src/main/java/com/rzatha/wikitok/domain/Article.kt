package com.rzatha.wikitok.domain

import com.rzatha.wikitok.data.PreviewArticleImageTitle

data class Article(
    val id: Int,
    val title: String,
    val extractText: String? = null,
    val imageTitle: List<PreviewArticleImageTitle>? = null,
    var imageUrl : String?  = null
)