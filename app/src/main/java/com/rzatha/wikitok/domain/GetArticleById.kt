package com.rzatha.wikitok.domain

class GetArticleById(
    private val repository: Repository
) {

    suspend operator fun invoke(id: Int): ArticlePreviewItem{
        return repository.getArticleById(id)
    }

}