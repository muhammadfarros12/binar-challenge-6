package com.farroos.movieapp_newfeatured.data.remote

import com.farroos.movieapp_newfeatured.data.remote.detail.DetailMovie
import com.farroos.movieapp_newfeatured.data.remote.home.Movie

class MovieRepository(private val movieRemoteDataSource: MovieRemoteDataSource) {

    suspend fun getNowPlayingMovie(): List<Movie>? {
        return movieRemoteDataSource.getMovies()
    }

    suspend fun getDetail(id: Int): DetailMovie {
        return movieRemoteDataSource.getDetail(id)
    }

}