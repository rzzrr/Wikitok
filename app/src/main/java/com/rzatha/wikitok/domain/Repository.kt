package com.rzatha.wikitok.domain

import androidx.lifecycle.LiveData

interface Repository {

    suspend fun getArticleById(id: Int): Article

    suspend fun loadRandomResponse()

    suspend fun addArticleToDb(article: Article)
    suspend fun removeArticleFromDb(article: Article)

    fun getFavouriteArticleListFromDb() : LiveData<List<Article>>

}