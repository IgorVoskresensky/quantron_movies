package ru.ivos.quantron_movies.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.ivos.quantron_movies.data.api.ApiRepo
import ru.ivos.quantron_movies.utils.MoviesPagingSource
import ru.ivos.quantron_movies.utils.TVPagingSource
import javax.inject.Inject

@HiltViewModel
class PagingViewModel @Inject constructor(
    private val apiRepo: ApiRepo
) : ViewModel() {

    val movieList = Pager(PagingConfig((1))) {
        MoviesPagingSource(apiRepo)
    }.flow.cachedIn(viewModelScope)

    val tvList = Pager(PagingConfig((1))) {
        TVPagingSource(apiRepo)
    }.flow.cachedIn(viewModelScope)
}