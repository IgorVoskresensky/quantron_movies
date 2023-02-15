package ru.ivos.quantron_movies.presentation.fragments

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import ru.ivos.quantron_movies.R
import ru.ivos.quantron_movies.data.models.favorite.Favorite
import ru.ivos.quantron_movies.data.models.movie.MovieDetails
import ru.ivos.quantron_movies.databinding.FragmentDetailsBinding
import ru.ivos.quantron_movies.presentation.viewmodels.FavoritesViewModel
import ru.ivos.quantron_movies.utils.Constants
import ru.ivos.quantron_movies.utils.gone
import ru.ivos.quantron_movies.utils.visible

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding: FragmentDetailsBinding
        get() = _binding ?: throw RuntimeException("Binding is empty")

    private var _movieDetails: MovieDetails? = null
    private val movieDetails: MovieDetails
        get() = _movieDetails ?: throw RuntimeException("Item is empty")

    private val favoritesList = MutableLiveData<List<Favorite>>()

    private val viewModel by viewModels<FavoritesViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            _movieDetails = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                it.getParcelable("details", MovieDetails::class.java)!!
            } else {
                @Suppress("DEPRECATION")
                it.getParcelable("details") as MovieDetails?
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkFavorites()

        with(binding) {
            Glide.with(requireContext()).load(
                "${Constants.POSTER_MAIN_URL}${movieDetails.posterPath}"
            ).into(ivPosterDetails)
            tvTitleDetails.text = if(movieDetails.title != "") movieDetails.title else movieDetails.name

            tvOverviewDetails.text = movieDetails.overview

            val res = if(movieDetails.rating >= 7.0) {
                R.drawable.bg_high_rating
            } else if (movieDetails.rating in 5.0..7.0) {
                R.drawable.bg_mid_rating
            } else {
                R.drawable.bg_low_rating
            }
            tvRatingDetails.setBackgroundResource(res)
            tvRatingDetails.text = movieDetails.rating.toString()

            popularity.text = movieDetails.popularity.toString()

            if(movieDetails.title != "") {
                releaseDate.text = movieDetails.releaseDate
            } else {
                llMovieDetails.gone()
                llTVDetails.visible()

                firstAirDate.text = movieDetails.firstAirDate
            }

            btnAddToFavorites.setOnClickListener {
                viewModel.insertFavorite(movieDetails)
                btnAddToFavorites.gone()
                btnRemoveFromFavorites.visible()
            }

            btnRemoveFromFavorites.setOnClickListener {
                viewModel.deleteFavorite(movieDetails.id)
                btnAddToFavorites.visible()
                btnRemoveFromFavorites.gone()
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun checkFavorites() {
        viewModel.getFavorites()
        var list = emptyList<Favorite>()
        viewModel.favorites.observe(viewLifecycleOwner) {
            favoritesList.value = it
            if(favoritesList.value != null) {
                list = favoritesList.value!!
            }
            val added = list.firstOrNull { it.id == movieDetails.id} != null

            if (added) {
                binding.btnAddToFavorites.gone()
                binding.btnRemoveFromFavorites.visible()
            }
        }

    }
}