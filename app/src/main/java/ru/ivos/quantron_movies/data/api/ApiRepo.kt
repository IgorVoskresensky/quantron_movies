package ru.ivos.quantron_movies.data.api

import javax.inject.Inject

class ApiRepo @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun getMovieList(page: Int = 1) =
        apiService.getMovieList(page)

    suspend fun getMovieSearch(query: String) =
        apiService.getMovieSearch(query)

    suspend fun getTVList(page: Int = 1) =
        apiService.getTVList(page)
}