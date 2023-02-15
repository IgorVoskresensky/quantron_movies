package ru.ivos.quantron_movies.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.ivos.quantron_movies.data.api.ApiRepo
import ru.ivos.quantron_movies.utils.UIStates
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val apiRepo: ApiRepo
) : ViewModel() {

    private var _movieState = MutableLiveData<UIStates>()
    val movieState: LiveData<UIStates> get() = _movieState

    fun getMovieList(query: String) {
        viewModelScope.launch {
            _movieState.value = UIStates.LOADING
            kotlin.runCatching {
                apiRepo.getMovieSearch(query)
            }.onSuccess {
                _movieState.value = UIStates.SUCCESS(it.body()!!)
            }.onFailure {
                _movieState.value = UIStates.FAILURE("message")
            }
        }
    }
}