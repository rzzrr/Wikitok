package com.rzatha.wikitok.data

import com.rzatha.wikitok.data.database.ArticleDbModel
import com.rzatha.wikitok.data.network.htmlResponse.ArticleHTMLDto
import com.rzatha.wikitok.domain.Article

class Mapper {

    fun mapArticlePreviewDtoToArticlePreview(dto: ArticlePreviewDto) : Article {
        return Article(
            id = dto.id,
            title = dto.title,
            extractText = dto.extractText,
            imageTitle = dto.images,
            imageUrl = dto.imageUrl
        )
    }

    fun mapArticleHTMLDtoToArticlePreview(dto: ArticleHTMLDto) : Article {
        return Article(
            id = dto.id,
            title = dto.title,
            extractText = dto.text.value,
            imageTitle = null,
            imageUrl =null
        )
    }

    fun mapArticleToArticleDbModel(article: Article): ArticleDbModel {
        return ArticleDbModel(
            id = article.id,
            title = article.title,
            imageUrl = article.imageUrl
        )
    }
}