package com.farroos.movieapp_newfeatured.data.remote.home


import com.google.gson.annotations.SerializedName

data class Movie(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("popularity")
    val popularity: Double?,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("vote_average")
    val voteAverage: Double?,
)