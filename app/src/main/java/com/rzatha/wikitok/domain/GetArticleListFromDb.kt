package com.rzatha.wikitok.domain

class GetArticleListFromDb(
    private val repository: Repository
) {

    operator fun invoke() = repository.getFavouriteArticleListFromDb()

}