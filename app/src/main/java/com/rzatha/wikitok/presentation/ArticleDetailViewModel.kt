package com.rzatha.wikitok.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rzatha.wikitok.data.repository.ArticleRepositoryImpl
import com.rzatha.wikitok.domain.ArticlePreviewItem
import com.rzatha.wikitok.domain.GetArticleById
import kotlinx.coroutines.launch

class ArticleDetailViewModel(
    val articleId: Int
) : ViewModel() {

    private val repository = ArticleRepositoryImpl()
    private val getArticleById = GetArticleById(repository)

    private val _ldArticle = MutableLiveData<ArticlePreviewItem>()
    val ldArticle : LiveData<ArticlePreviewItem>
        get() = _ldArticle

    init {
        viewModelScope.launch {
            _ldArticle.postValue(getArticleById.invoke(articleId))
        }
    }

}