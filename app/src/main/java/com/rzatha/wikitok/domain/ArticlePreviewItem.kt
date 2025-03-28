package com.rzatha.wikitok.domain

import com.rzatha.wikitok.data.PreviewArticleImageTitle

data class ArticlePreviewItem(
    val id: Int,
    val title: String,
    val extractText: String?,
    val imageTitle: List<PreviewArticleImageTitle>?,
    var imageUrl : String?
)