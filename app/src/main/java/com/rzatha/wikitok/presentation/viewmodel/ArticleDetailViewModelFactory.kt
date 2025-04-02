package com.rzatha.wikitok.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras

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