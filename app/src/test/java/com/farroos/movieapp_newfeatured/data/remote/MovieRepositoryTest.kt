package com.farroos.movieapp_newfeatured.data.remote

import com.farroos.movieapp_newfeatured.data.remote.home.MovieResponse
import com.farroos.movieapp_newfeatured.data.remote.service.ApiService
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test


internal class MovieRepositoryTest {

    private lateinit var service: ApiService
    private lateinit var repository: MovieRepository
    private lateinit var dataSource: MovieRemoteDataSource

    @Before
    fun setUp() {
        service = mockk()
        dataSource = MovieRemoteDataSource()
        repository = MovieRepository(dataSource)
    }

    @Test
    fun getMovie (): Unit = runBlocking {
        val response = mockk<MovieResponse>()

        every {
            runBlocking {
                service.getMovie()
            }
        } returns response

        repository.getNowPlayingMovie()

        verify {
            runBlocking { service.getMovie() }
        }
    }

}