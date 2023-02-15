package ru.ivos.quantron_movies.data.models.movie

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class MovieResponse(
        @SerializedName("page")
        @Expose
        val page: Int,
        @SerializedName("results")
        @Expose
        val list: List<MovieDetails>
)