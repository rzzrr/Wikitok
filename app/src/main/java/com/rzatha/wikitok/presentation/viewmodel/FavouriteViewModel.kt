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

    fun removeArticle(article: Article) {
        viewModelScope.launch {
            RemoveArticleFromDb(repository).invoke(article)
        }
    }

}