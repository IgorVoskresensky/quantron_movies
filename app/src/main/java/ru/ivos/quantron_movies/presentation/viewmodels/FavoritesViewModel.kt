package ru.ivos.quantron_movies.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.ivos.quantron_movies.data.db.DataRepo
import ru.ivos.quantron_movies.data.models.favorite.Favorite
import ru.ivos.quantron_movies.data.models.movie.MovieDetails
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val dataRepo: DataRepo
) : ViewModel() {

    private var _favorites = MutableLiveData<List<Favorite>>()
    val favorites: LiveData<List<Favorite>> get() = _favorites

    fun getFavorites() = viewModelScope.launch(Dispatchers.IO) {
        val favoritesLIst = dataRepo.getFavorites()
        _favorites.postValue(favoritesLIst)
    }

    fun insertFavorite(movieDetails: MovieDetails) = viewModelScope.launch(Dispatchers.IO) {
        dataRepo.insertFavorite(movieDetails)
    }

    fun deleteFavorite(id: Int) = viewModelScope.launch(Dispatchers.IO) {
        dataRepo.deleteFavorite(id)
    }
}