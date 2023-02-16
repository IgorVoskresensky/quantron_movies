package ru.ivos.quantron_movies.data.db

import ru.ivos.quantron_movies.data.models.favorite.Favorite
import ru.ivos.quantron_movies.data.models.movie.MovieDetails

class FavoriteMapper {

    fun mapMovieDetailsToFavorite(movieDetails: MovieDetails) : Favorite {
        return Favorite(
            id = movieDetails.id,
            originalTitle = movieDetails.originalTitle,
            overview = movieDetails.overview,
            popularity = movieDetails.popularity,
            posterPath = movieDetails.posterPath,
            releaseDate = movieDetails.releaseDate,
            title = movieDetails.title,
            rating = movieDetails.rating,
            originalName = movieDetails.originalName,
            numberOfSeasons = movieDetails.numberOfSeasons,
            numberOfEpisodes = movieDetails.numberOfEpisodes,
            firstAirDate = movieDetails.firstAirDate,
            name = movieDetails.name
        )
    }

    fun mapFavoriteDetailsToMovieDetails(favorite: Favorite) : MovieDetails {
        return MovieDetails(
            id = favorite.id,
            originalTitle = favorite.originalTitle,
            overview = favorite.overview,
            popularity = favorite.popularity,
            posterPath = favorite.posterPath,
            releaseDate = favorite.releaseDate,
            title = favorite.title,
            rating = favorite.rating,
            originalName = favorite.originalName,
            numberOfSeasons = favorite.numberOfSeasons,
            numberOfEpisodes = favorite.numberOfEpisodes,
            firstAirDate = favorite.firstAirDate,
            name = favorite.name
        )
    }
}