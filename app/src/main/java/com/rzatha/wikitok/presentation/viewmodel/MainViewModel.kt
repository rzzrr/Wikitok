package com.rzatha.wikitok.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.rzatha.wikitok.data.repository.ArticleRepositoryImpl
import com.rzatha.wikitok.domain.AddArticleToDb
import com.rzatha.wikitok.domain.Article
import com.rzatha.wikitok.domain.LoadRandomResponse
import com.rzatha.wikitok.domain.RemoveArticleFromDb
import kotlinx.coroutines.launch

class MainViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val repository = ArticleRepositoryImpl(application)

    val articleItemList = repository.articlePreviewList

    val favouriteArticleIdList = repository.favouriteArticleIdList

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    fun loadRandomResponse() {
        if (_isLoading.value == false) {
            _isLoading.value = true
            viewModelScope.launch {
                LoadRandomResponse(repository).invoke()
                _isLoading.postValue(false)
            }
        }
    }

    fun removeFromDb(article: Article) {
        viewModelScope.launch {
           RemoveArticleFromDb(repository).invoke(article)
        }
    }

    fun addToDb(article: Article){
        viewModelScope.launch {
            AddArticleToDb(repository).invoke(article)
        }
    }

    init {
        loadRandomResponse()
    }

}