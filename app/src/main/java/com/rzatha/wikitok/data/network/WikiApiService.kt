package com.rzatha.wikitok.data.network

import retrofit2.http.GET
import retrofit2.http.Query

interface WikiApiService {

    @GET("api.php")
    suspend fun getRandomResponse(
        @Query(QUERY_PARAM_ACTION) action: String = "query",
        @Query(QUERY_PARAM_FORMAT) format: String = "json",
        @Query(QUERY_PARAM_PROP) prop: String = "extracts|images",
        @Query(QUERY_PARAM_GENERATOR) generator: String = "random",
        @Query(QUERY_PARAM_FORMAT_VERSION) formatVersion: Int = 2,
        @Query(QUERY_PARAM_FORMAT_EX_INTRO) exIntro: Int = 1,
        @Query(QUERY_PARAM_EXPLAIN_TEXT) explainText: Int = 1,
        @Query(QUERY_PARAM_EX_SECTION_FORMAT) exSectionFormat: String = "plain",
        @Query(QUERY_PARAM_GRN_NAMESPACE) grnNamespace: Int = 0,
        @Query(QUERY_PARAM_GRN_LIMIT) grnLimit: Int = 15,
        @Query(QUERY_PARAM_IM_LIMIT) imLimit: Int = MAX_IM_LIMIT_VALUE,
    ) : RandomPageResponse

    @GET("api.php")
    suspend fun getArticleImage(
        @Query(QUERY_PARAM_ACTION) action: String = "query",
        @Query(QUERY_PARAM_TITLES) titles: String,
        @Query(QUERY_PARAM_PROP) prop: String = "imageinfo",
        @Query(QUERY_PARAM_II_PROP) iiProp: String = "url",
        @Query(QUERY_PARAM_FORMAT) format: String = "json"
    ) : ImageResponse

    companion object {
        private const val QUERY_PARAM_ACTION = "action"
        private const val QUERY_PARAM_FORMAT = "format"
        private const val QUERY_PARAM_PROP= "prop"
        private const val QUERY_PARAM_GENERATOR = "generator"
        private const val QUERY_PARAM_FORMAT_VERSION = "formatversion"
        private const val QUERY_PARAM_FORMAT_EX_INTRO = "exintro"
        private const val QUERY_PARAM_EXPLAIN_TEXT = "explaintext"
        private const val QUERY_PARAM_EX_SECTION_FORMAT = "exsectionformat"
        private const val QUERY_PARAM_GRN_NAMESPACE = "grnnamespace"
        private const val QUERY_PARAM_GRN_LIMIT = "grnlimit"
        private const val QUERY_PARAM_TITLES = "titles"
        private const val QUERY_PARAM_II_PROP = "iiprop"
        private const val QUERY_PARAM_IM_LIMIT = "imlimit"
        private const val MAX_IM_LIMIT_VALUE = 500

    }

}