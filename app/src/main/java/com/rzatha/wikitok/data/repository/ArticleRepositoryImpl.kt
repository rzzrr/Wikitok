package com.rzatha.wikitok.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.rzatha.wikitok.data.Mapper
import com.rzatha.wikitok.data.network.WikiApiFactory
import com.rzatha.wikitok.domain.ArticlePreviewItem
import com.rzatha.wikitok.domain.Repository

class ArticleRepositoryImpl() : Repository {

    private val wikiApiService = WikiApiFactory.apiService
    private val mapper = Mapper()
    private val _articlePreviewList = MutableLiveData<MutableList<ArticlePreviewItem>>()
    val articlePreviewList: LiveData<List<ArticlePreviewItem>>
        get() {
            return _articlePreviewList.map {
                it.toList()
            }
        }

    override suspend fun getArticleById(id: Int): ArticlePreviewItem {
        val res = wikiApiService.getArticleById(pageIds = id)
            .queryPages
            .pageMap[id] ?: throw RuntimeException("Article item is null")
        return mapper.mapArticlePreviewDtoToArticlePreview(res)
    }

    override suspend fun loadRandomResponse() {
        Log.d("MainActivity", "loadRandomResponse")

        val newList = _articlePreviewList.value ?: mutableListOf()
        val prevSize = articlePreviewList.value?.size ?: 0

        while (newList.size <= prevSize + 6) {
            try {
                val loadedArticles =
                    wikiApiService.getRandomResponse()
                        .queryPages
                        .pages
                        .map {
                            mapper.mapArticlePreviewDtoToArticlePreview(it)
                        }

                newList.addAll(validateArticles(loadedArticles))

            } catch (e: Exception) {
                Log.d("MainActivity", "${e.message}")
            }
        }

        bindImageUrl(newList.toList())
        _articlePreviewList.postValue(newList)

    }

    private fun validateArticles(articles: List<ArticlePreviewItem>) =
        articles.filter { articleItem ->
            articleItem.imageTitle != null && articleItem.imageTitle.any {
                it.title.contains("jpg")
            }
        }

    private suspend fun bindImageUrl(articles: List<ArticlePreviewItem>) {

        articles.forEach { articleItem ->
            articleItem.imageTitle?.let {
                try {
                    val imageTitle = it.first {
                        it.title.contains("jpg")
                    }

                    val artImage =
                        wikiApiService.getArticleImage(titles = imageTitle.title)
                            .queryImages
                            .imgPages.imagePage.imageInfoList.first()

                    articleItem.imageUrl = artImage.url

                } catch (e: Exception) {
                    Log.d("MainActivity", "HERE ${e.message}")
                }

            }

        }
    }

}