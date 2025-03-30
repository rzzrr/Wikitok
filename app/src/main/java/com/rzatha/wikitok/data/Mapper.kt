package com.rzatha.wikitok.data

import com.rzatha.wikitok.data.network.htmlResponse.ArticleHTMLDto
import com.rzatha.wikitok.domain.ArticlePreviewItem

class Mapper {

    fun mapArticlePreviewDtoToArticlePreview(dto: ArticlePreviewDto) : ArticlePreviewItem {
        return ArticlePreviewItem(
            id = dto.id,
            title = dto.title,
            extractText = dto.extractText,
            imageTitle = dto.images,
            imageUrl = dto.imageUrl
        )
    }

    fun mapArticleHTMLDtoToArticlePreview(dto: ArticleHTMLDto) : ArticlePreviewItem {
        return ArticlePreviewItem(
            id = dto.id,
            title = dto.title,
            extractText = dto.text.value,
            imageTitle = null,
            imageUrl =null
        )
    }
}