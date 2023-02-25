package com.example.scalex.Interface

import com.example.scalex.NewsJSONModel
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

const val BASE_URL = "https://newsapi.org/"
const val API_KEY = "9bd814c25376407986991239a99329ff"

interface NewsInterface {
    @GET("v2/top-headlines?apikey=$API_KEY")
    fun getHeadLines(
        @Query("country") country: String,
        @Query("page") page: Int
    ): Call<NewsJSONModel>
}

object NewsService {
    val newsInstance: NewsInterface

    init {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
            .build()
        newsInstance = retrofit.create(NewsInterface::class.java)
    }
}