package com.rzatha.wikitok.data.network.randomArtResponse

import com.google.gson.annotations.SerializedName

data class RandomPageResponse(
    @SerializedName("query") val queryPages: QueryPages
)