package ru.ivos.quantron_movies.data.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import ru.ivos.quantron_movies.data.models.movie.MovieResponse

interface ApiService {

    @GET("movie/popular?api_key=02b113b496621e5a49428c55c55a3ccc")
    suspend fun getMovieList(
        @Query("page") page: Int = 1
    ): Response<MovieResponse>

    @GET("search/movie?api_key=02b113b496621e5a49428c55c55a3ccc")
    suspend fun getMovieSearch(
        @Query("query") query: String
    ): Response<MovieResponse>

    @GET("tv/popular?api_key=02b113b496621e5a49428c55c55a3ccc")
    suspend fun getTVList(
        @Query("page") page: Int = 1
    ): Response<MovieResponse>

    /**
     * The search for TV shows in the API is performed using the TV show's ID.
     * I couldn't figure out how the user could know this ID for the request
     */
}