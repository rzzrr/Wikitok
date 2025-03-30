package com.rzatha.wikitok.data.network.artResponse

import com.google.gson.annotations.SerializedName

class ArticleResponse(
    @SerializedName("query") val queryPages: QueryPages
)