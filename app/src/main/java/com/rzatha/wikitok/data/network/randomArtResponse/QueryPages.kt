package com.rzatha.wikitok.data.network.randomArtResponse

import com.google.gson.annotations.SerializedName
import com.rzatha.wikitok.data.ArticlePreviewDto

data class QueryPages(
    @SerializedName("pages") val pages: List<ArticlePreviewDto>
)
