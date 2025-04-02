package com.rzatha.wikitok.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.rzatha.wikitok.data.repository.ArticleRepositoryImpl
import com.rzatha.wikitok.domain.Article
import com.rzatha.wikitok.domain.GetArticleById
import kotlinx.coroutines.launch

class ArticleDetailViewModel(
    private val articleId: Int,
    application: Application
) : AndroidViewModel(application) {

    private val repository = ArticleRepositoryImpl(application)
    private val getArticleById = GetArticleById(repository)

    private val _ldArticle = MutableLiveData<Article>()
    val ldArticle : LiveData<Article>
        get() = _ldArticle

    init {
        viewModelScope.launch {
            _ldArticle.postValue(getArticleById.invoke(articleId))
        }
    }

}