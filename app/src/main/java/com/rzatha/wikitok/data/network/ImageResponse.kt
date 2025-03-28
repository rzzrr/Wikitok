package com.rzatha.wikitok.data.network

import com.google.gson.annotations.SerializedName

class ImageResponse(
    @SerializedName("query") val queryImages: QueryImages
)