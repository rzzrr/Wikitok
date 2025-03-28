package com.rzatha.wikitok.domain

interface Repository {

    suspend fun getArticleById(id: Int): ArticlePreviewItem

    suspend fun loadRandomResponse()
}