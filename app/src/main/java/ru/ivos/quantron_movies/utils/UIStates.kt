package ru.ivos.quantron_movies.utils

import ru.ivos.quantron_movies.data.models.movie.MovieResponse

sealed class UIStates {
    object LOADING : UIStates()
    class FAILURE(val message: String) : UIStates()
    class SUCCESS(val movieResponse: MovieResponse) : UIStates()
}

//sealed class TVStates {
//    object LOADING : TVStates()
//    class FAILURE(val message: String) : TVStates()
//    class SUCCESS(val tvResponse: TVResponse) : TVStates()
//}