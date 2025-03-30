package com.rzatha.wikitok.data.network.artResponse

import com.google.gson.annotations.SerializedName
import com.rzatha.wikitok.data.ArticlePreviewDto

data class QueryPages(
    @SerializedName("pages") val pageMap: Map<Int, ArticlePreviewDto>
)
