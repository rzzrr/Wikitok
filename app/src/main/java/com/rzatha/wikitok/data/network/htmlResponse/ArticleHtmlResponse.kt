package com.rzatha.wikitok.data.network.htmlResponse

import com.google.gson.annotations.SerializedName

class ArticleHtmlResponse(
    @SerializedName("parse") val parse : ArticleHTMLDto
)