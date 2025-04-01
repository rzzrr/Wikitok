package com.rzatha.wikitok.presentation

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import java.lang.Appendable

class ArticleDetailViewModelFactory(
    val articleId: Int,
    val application: Application
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        if (modelClass.isAssignableFrom(ArticleDetailViewModel::class.java)) {
            return ArticleDetailViewModel(articleId, application) as T
        }
        throw RuntimeException("Unknown view model class $modelClass")
    }}