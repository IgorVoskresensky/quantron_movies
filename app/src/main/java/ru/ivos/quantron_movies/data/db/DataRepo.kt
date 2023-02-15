package ru.ivos.quantron_movies.data.db

import ru.ivos.quantron_movies.data.models.favorite.Favorite
import ru.ivos.quantron_movies.data.models.movie.MovieDetails
import javax.inject.Inject

class DataRepo @Inject constructor(
    private val appDao: AppDao
) {

    suspend fun getFavorites(): List<Favorite> {
        return appDao.getFavorites()
    }

    suspend fun insertFavorite(movieDetails: MovieDetails) {
        val favorite = FavoriteMapper().mapMovieDetailsToFavorite(movieDetails)
        appDao.insertFavorite(favorite)
    }

    suspend fun deleteFavorite(id: Int) {
        appDao.deleteFavorite(id)
    }
}