package com.rzatha.wikitok.domain

class RemoveArticleFromDb(
    private val repository: Repository
) {
    suspend operator fun invoke(article: Article) {
        repository.removeArticleFromDb(article)
    }
}