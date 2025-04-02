package com.rzatha.wikitok.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ArticleDao {

    @Query("select * from articles")
    fun getAllArticles() : LiveData<List<ArticleDbModel>>

    @Query("select id from articles")
    fun getAllArticlesId(): LiveData<List<Int>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticle(article: ArticleDbModel)

    @Query("delete from articles where id = :id")
    suspend fun deleteArticle(id: Int)

}