package com.farroos.movieapp_newfeatured.data.remote.home


import com.farroos.movieapp_newfeatured.data.remote.home.Movie
import com.google.gson.annotations.SerializedName

data class MovieResponse(
    @SerializedName("page")
    val page: Int?,
    @SerializedName("results")
    val results: List<Movie>?,
    @SerializedName("total_pages")
    val totalPages: Int?,
    @SerializedName("total_results")
    val totalResults: Int?
)