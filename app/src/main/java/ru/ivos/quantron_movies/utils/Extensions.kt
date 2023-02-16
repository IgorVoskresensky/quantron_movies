package ru.ivos.quantron_movies.utils

import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import ru.ivos.quantron_movies.R
import ru.ivos.quantron_movies.data.db.FavoriteMapper
import ru.ivos.quantron_movies.data.models.favorite.Favorite
import ru.ivos.quantron_movies.data.models.movie.MovieDetails

fun AppCompatActivity.replaceFragment(fragment: Fragment, addToBackStack: Boolean = true) {
    if(addToBackStack) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .addToBackStack(null)
            .commit()
    } else {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }
}

fun Fragment.replaceFragment(fragment: Fragment, addToBackStack: Boolean = true) {
    if(addToBackStack) {
        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.fragmentContainer, fragment)
            ?.addToBackStack(null)
            ?.commit()
    } else {
        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.fragmentContainer, fragment)
            ?.commit()
    }
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun mapToMovieDetails(list: List<Favorite>): List<MovieDetails> {
    return list.map { FavoriteMapper().mapFavoriteDetailsToMovieDetails(it) }
}