package ru.ivos.quantron_movies.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.ivos.quantron_movies.R
import ru.ivos.quantron_movies.data.models.movie.MovieDetails
import ru.ivos.quantron_movies.databinding.FragmentFavoritesBinding
import ru.ivos.quantron_movies.presentation.adapters.SearchAndFavoritesAdapter
import ru.ivos.quantron_movies.presentation.viewmodels.FavoritesViewModel
import ru.ivos.quantron_movies.utils.gone
import ru.ivos.quantron_movies.utils.mapToMovieDetails

@AndroidEntryPoint
class FavoritesFragment : Fragment() {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding: FragmentFavoritesBinding
        get() = _binding ?: throw RuntimeException("Binding is empty")

    private lateinit var adapter: SearchAndFavoritesAdapter

    private val viewModel by viewModels<FavoritesViewModel>()
    private var movieList = emptyList<MovieDetails>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = SearchAndFavoritesAdapter()
        checkFavorites()

        binding.rvFavorites.adapter = adapter

        adapter.setOnItemClickListener {
            findNavController().navigate(
                R.id.action_favoritesFragment_to_detailsFragment,
                bundleOf(Pair("details", it))
            )
        }

        binding.btnMoviesFavorites.setOnClickListener {
            val movieListMovie = movieList.filter { it.name == "" }
            adapter.differ.submitList(movieListMovie)
        }
        binding.btnTVFavorites.setOnClickListener {
            val movieListMovie = movieList.filter { it.title == "" }
            adapter.differ.submitList(movieListMovie)
        }
    }

    private fun checkFavorites() {
        viewModel.getFavorites()
        viewModel.favorites.observe(viewLifecycleOwner) { list ->
            if(list.isNotEmpty()) {
                binding.tvFavoritesText.gone()
            }
            movieList = mapToMovieDetails(list)
            val movieListMovie = movieList.filter { it.name == "" }
            adapter.differ.submitList(movieListMovie)
        }

    }

}