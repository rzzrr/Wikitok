package com.rzatha.wikitok.data.network.imageResponse

import com.google.gson.annotations.SerializedName
import com.rzatha.wikitok.data.network.ImageInfo

class ImgPage (
    @SerializedName("imageinfo") val imageInfoList: List<ImageInfo>
)