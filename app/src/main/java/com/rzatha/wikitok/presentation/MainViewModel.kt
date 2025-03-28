package com.rzatha.wikitok.presentation

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.rzatha.wikitok.data.repository.ArticleRepositoryImpl
import com.rzatha.wikitok.domain.GetArticleById
import com.rzatha.wikitok.domain.LoadRandomResponse
import kotlinx.coroutines.launch

class MainViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val repository = ArticleRepositoryImpl()
    private val getArticleById = GetArticleById(repository)
    val articlePreviewItemList = repository.articlePreviewList

    fun loadRandomResponse() {
        viewModelScope.launch {
            LoadRandomResponse(repository).invoke()
        }
    }

    init {
        loadRandomResponse()
    }

}