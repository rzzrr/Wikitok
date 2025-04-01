package com.rzatha.wikitok.data.database

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ArticleDbModel::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun articleDao() : ArticleDao

    companion object {

        private var INSTANCE: AppDatabase? = null
        private val LOCK = Any()
        private const val DB_NAME = "fav_articles.db"

        fun getInstance(application: Application): AppDatabase{

            INSTANCE?.let {
                return it
            }

            synchronized(LOCK){
                INSTANCE?.let {
                    return it
                }

                val db = Room.databaseBuilder(
                    application,
                    AppDatabase::class.java,
                    DB_NAME
                ).build()

                INSTANCE = db
                return db
            }

        }

    }

}