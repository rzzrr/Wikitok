package com.rzatha.wikitok.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.rzatha.wikitok.data.repository.ArticleRepositoryImpl
import com.rzatha.wikitok.domain.Article
import com.rzatha.wikitok.domain.GetArticleListFromDb
import com.rzatha.wikitok.domain.RemoveArticleFromDb
import kotlinx.coroutines.launch

class FavouriteViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val repository = ArticleRepositoryImpl(application)
    val getArticleListFromDb = GetArticleListFromDb(repository).invoke()

    private val _originalList = MutableLiveData<List<Article>>()
    private val _filteredList = MutableLiveData<List<Article>>()
    val filteredList : LiveData<List<Article>>
        get() = _filteredList

    fun removeArticle(article: Article) {
        viewModelScope.launch {
            RemoveArticleFromDb(repository).invoke(article)
        }
    }

    fun filter(substring: String){
        val currentList = _originalList.value ?: return
        if (substring.isEmpty()) {
            _filteredList.value = currentList
        } else {
            val filtered = currentList.filter {
                it.title.contains(substring, ignoreCase = true)
            }
            _filteredList.value = filtered
        }
    }

    fun setOriginalList(it: List<Article>) {
        _originalList.value = it
        _filteredList.value = it
    }

}