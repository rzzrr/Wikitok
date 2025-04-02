package com.rzatha.wikitok.data

import com.rzatha.wikitok.data.database.ArticleDbModel
import com.rzatha.wikitok.data.network.htmlResponse.ArticleHTMLDto
import com.rzatha.wikitok.domain.Article

class Mapper {

    fun mapArticlePreviewDtoToArticlePreview(dto: ArticlePreviewDto) = Article(
        id = dto.id,
        title = dto.title,
        extractText = dto.extractText,
        imageTitle = dto.images,
        imageUrl = dto.imageUrl
    )


    fun mapArticleHTMLDtoToArticlePreview(dto: ArticleHTMLDto) = Article(
        id = dto.id,
        title = dto.title,
        extractText = dto.text.value,
        imageTitle = null,
        imageUrl = null
    )


    fun mapArticleToArticleDbModel(article: Article) = ArticleDbModel(
        id = article.id,
        title = article.title,
        imageUrl = article.imageUrl
    )


    fun mapArticleDbModelToArticle(dbModel: ArticleDbModel) = Article(
        id = dbModel.id,
        title = dbModel.title,
        imageUrl = dbModel.imageUrl
    )

    fun mapArticleDbModelListToArticleList(articleDbModelList: List<ArticleDbModel>): List<Article> {
        return articleDbModelList.map {
            mapArticleDbModelToArticle(it)
        }
    }
}