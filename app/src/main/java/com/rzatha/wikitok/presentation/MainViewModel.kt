package com.rzatha.wikitok.presentation

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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

    init {
        loadRandomResponse()
    }

}