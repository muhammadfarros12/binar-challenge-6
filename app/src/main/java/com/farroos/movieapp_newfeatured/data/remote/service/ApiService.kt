@file:Suppress("unused", "unused", "unused", "unused", "unused", "unused", "unused")

package com.farroos.movieapp_newfeatured.data.remote.service

import com.farroos.movieapp_newfeatured.data.remote.home.MovieResponse
import com.farroos.movieapp_newfeatured.data.remote.detail.DetailMovie
import com.farroos.movieapp_newfeatured.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface ApiService {

    @GET("movie/now_playing")
    suspend fun getMovie(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ): MovieResponse

    @GET("movie/{movieId}")
    suspend fun getDetailMovie(
        @Path("movieId") movieId: Int,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ): Response<DetailMovie>
}

@Suppress("unused", "unused", "unused", "unused", "unused", "unused", "unused")
object ApiClient {
    private val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
    private val client = OkHttpClient.Builder()
        .addInterceptor(logging)
        .readTimeout(30, TimeUnit.SECONDS)
        .build()

    val instance: ApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        retrofit.create(ApiService::class.java)
    }

}
