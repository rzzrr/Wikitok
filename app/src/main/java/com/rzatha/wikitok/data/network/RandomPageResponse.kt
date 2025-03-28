package com.rzatha.wikitok.data.network

import com.google.gson.annotations.SerializedName

data class RandomPageResponse(
    @SerializedName("query") val queryPages: QueryPages
)