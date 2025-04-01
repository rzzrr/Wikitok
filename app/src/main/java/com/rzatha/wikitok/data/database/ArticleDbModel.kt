package com.rzatha.wikitok.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "articles")
data class ArticleDbModel(
    @PrimaryKey
    val id: Int,

    @SerializedName("title")
    val title: String,

    @SerializedName("image_url")
    var imageUrl : String?
)