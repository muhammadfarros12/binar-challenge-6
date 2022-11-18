package com.farroos.movieapp_newfeatured.data.remote

import com.farroos.movieapp_newfeatured.data.remote.detail.DetailMovie
import com.farroos.movieapp_newfeatured.data.remote.home.Movie
import com.farroos.movieapp_newfeatured.data.remote.service.ApiClient

class MovieRemoteDataSource {

    suspend fun getMovies(): List<Movie>? {
        try {
            return ApiClient.instance.getMovie().results
        } catch (e: Throwable){
            throw ErrorMovie("Data gagal dimuat", e)
        }
    }

    suspend fun getDetail(id: Int): DetailMovie {
        try {
            return ApiClient.instance.getDetailMovie(id).body()!!
        } catch (e: Throwable) {
            throw ErrorMovie("data gagal di load", e)
        }
    }

}

class ErrorMovie(message: String, cause: Throwable?) : Throwable(message, cause)