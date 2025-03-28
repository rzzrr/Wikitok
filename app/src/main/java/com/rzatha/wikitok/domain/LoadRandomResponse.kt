package com.rzatha.wikitok.domain

class LoadRandomResponse(
    private val repository: Repository
) {
    suspend operator fun invoke(){
        repository.loadRandomResponse()
    }
}