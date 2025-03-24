package com.rzatha.wikitok.domain

data class ArticlePreviewItem(
    val id : Int,
    val title: String,
    val text : String,
    val image : Image,
    val source : String
)