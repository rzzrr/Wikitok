package com.rzatha.wikitok.domain

interface Repository {

    suspend fun getArticleById(id: Int): Article

    suspend fun loadRandomResponse()
}