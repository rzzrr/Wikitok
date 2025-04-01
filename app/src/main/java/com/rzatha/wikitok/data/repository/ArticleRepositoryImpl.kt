package com.rzatha.wikitok.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.rzatha.wikitok.data.Mapper
import com.rzatha.wikitok.data.network.WikiApiFactory
import com.rzatha.wikitok.domain.Article
import com.rzatha.wikitok.domain.Repository

class ArticleRepositoryImpl() : Repository {

    private val wikiApiService = WikiApiFactory.apiService
    private val mapper = Mapper()
    private val _articlePreviewList = MutableLiveData<MutableList<Article>>()
    val articlePreviewList: LiveData<List<Article>>
        get() {
            return _articlePreviewList.map {
                it.toList()
            }
        }

    override suspend fun getArticleById(id: Int): Article {
        val res = wikiApiService.getArticleHTMLById(pageId = id).parse
        return mapper.mapArticleHTMLDtoToArticlePreview(res)
    }

    override suspend fun loadRandomResponse() {
        Log.d("MainActivity", "loadRandomResponse")

        val newList = _articlePreviewList.value ?: mutableListOf()
        val prevSize = articlePreviewList.value?.size ?: 0

        while (newList.size <= prevSize + NEW_PAGES_MIN) {
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

    private fun validateArticles(articles: List<Article>) =
        articles.filter { articleItem ->
            articleItem.imageTitle != null && articleItem.imageTitle.any {
                it.title.contains("jpg")
            }
        }

    private suspend fun bindImageUrl(articles: List<Article>) {

        articles.forEach { articleItem ->
            articleItem.imageTitle?.let {
                try {
                    val imageTitle = it.first {
                        it.title.contains("jpg") || it.title.contains("png") || it.title.contains("jpeg")
                    }

                    val artImage =
                        wikiApiService.getArticleImage(titles = imageTitle.title)
                            .queryImages
                            .imgPages.imagePage.imageInfoList.first()

                    articleItem.imageUrl = artImage.url

                } catch (e: Exception) {
                    Log.d("MainActivity", "Bind image error. Article id: ${articleItem.id}: ${e.message}")
                }

            }

        }
    }

    companion object {
        private const val NEW_PAGES_MIN = 8
    }

}