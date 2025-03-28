package com.rzatha.wikitok.domain

class GetArticleById(
    private val repository: Repository
) {

    suspend operator fun invoke(id: Int){
        repository.getArticleById(id)
    }

}