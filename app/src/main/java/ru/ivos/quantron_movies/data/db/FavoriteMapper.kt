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
}