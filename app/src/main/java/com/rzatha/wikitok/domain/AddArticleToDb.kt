package com.rzatha.wikitok.domain

class AddArticleToDb(
    private val repository: Repository
) {
    suspend operator fun invoke(article: Article) {
        repository.addArticleToDb(article)
    }
}