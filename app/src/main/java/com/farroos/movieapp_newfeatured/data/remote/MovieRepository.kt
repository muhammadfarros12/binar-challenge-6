package com.farroos.movieapp_newfeatured.data.remote

import com.farroos.movie.data.remote.detail.DetailMovie
import com.farroos.movieapp_newfeatured.data.remote.home.Movie

class MovieRepository(private val movieRemoteDataSource: MovieRemoteDataSource) {

    suspend fun getNowPlayingMovie(): List<Movie>? {
        return movieRemoteDataSource.getMovies()
    }

    suspend fun getDetail(id: Int): DetailMovie {
        return movieRemoteDataSource.getDetail(id)
    }

    companion object {
        @Volatile
        private var instance: MovieRepository? = null
        fun getInstance(
            remoteDataSource: MovieRemoteDataSource
        ): MovieRepository =
            instance ?: synchronized(this) {
                instance ?: MovieRepository(remoteDataSource)
            }.also { instance = it }
    }

}