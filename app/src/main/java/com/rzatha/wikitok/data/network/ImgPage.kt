package com.rzatha.wikitok.data.network

import com.google.gson.annotations.SerializedName

class ImgPage (
    @SerializedName("imageinfo") val imageInfoList: List<ImageInfo>
)