package ru.ivos.quantron_movies.utils

import androidx.paging.PagingSource
import androidx.paging.PagingState
import ru.ivos.quantron_movies.data.api.ApiRepo
import ru.ivos.quantron_movies.data.models.movie.MovieDetails

class TVPagingSource (
    private val repo: ApiRepo
) : PagingSource<Int, MovieDetails>() {

    override fun getRefreshKey(state: PagingState<Int, MovieDetails>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieDetails> {
        return try {
            val currentPage = params.key ?: 1
            val response = repo.getTVList(currentPage)
            val data = response.body()!!.list
            val responseData = mutableListOf<MovieDetails>()
            responseData.addAll(data)

            LoadResult.Page(
                data = responseData,
                prevKey = if(currentPage == 1) null else -1,
                nextKey = currentPage.plus(1)
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}