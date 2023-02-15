package ru.ivos.quantron_movies.presentation

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
class MainViewModel @Inject constructor(
    private val apiRepo: ApiRepo
) : ViewModel(){

    private var _movieState = MutableLiveData<UIStates>()
    val movieState: LiveData<UIStates> get() = _movieState

    private var _tvState = MutableLiveData<UIStates>()
    val tvState: LiveData<UIStates> get() = _tvState

    init {
        getMovieList()
    }

    fun getMovieList() {
        viewModelScope.launch {
            _movieState.value = UIStates.LOADING
            kotlin.runCatching {
                apiRepo.getMovieList()
            }.onSuccess {
                _movieState.value = UIStates.SUCCESS(it.body()!!)
            }.onFailure {
                _movieState.value = UIStates.FAILURE("message")
            }
        }
    }

    fun getTVList() {
        viewModelScope.launch {
            _tvState.value = UIStates.LOADING
            kotlin.runCatching {
                apiRepo.getTVList()
            }.onSuccess {
                _tvState.value = UIStates.SUCCESS(it.body()!!)
            }.onFailure {
                _tvState.value = UIStates.FAILURE("message")
            }
        }
    }
}