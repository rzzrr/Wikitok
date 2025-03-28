package com.rzatha.wikitok.data.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object WikiApiFactory {

    private const val BASE_URL = "https://ru.wikipedia.org/w/"

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .build()

    val apiService = retrofit.create(WikiApiService::class.java)
}