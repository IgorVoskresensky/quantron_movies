package ru.ivos.quantron_movies.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import ru.ivos.quantron_movies.R
import ru.ivos.quantron_movies.databinding.FragmentHomeBinding
import ru.ivos.quantron_movies.presentation.viewmodels.PagingViewModel
import ru.ivos.quantron_movies.presentation.adapters.HomePagingAdapter
import ru.ivos.quantron_movies.utils.gone
import ru.ivos.quantron_movies.utils.visible

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding
        get() = _binding ?: throw RuntimeException("Binding is empty")

    //    private val viewModel by viewModels<MainViewModel>()
    private val viewModel by viewModels<PagingViewModel>()

    private lateinit var adapter: HomePagingAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = HomePagingAdapter()

        getMovies()

        adapter.setOnItemClickListener {
            findNavController().navigate(
                R.id.action_homeFragment_to_detailsFragment,
                bundleOf(Pair("details", it))
                )
        }

        binding.btnMoviesHome.setOnClickListener {
            getMovies()
        }
        binding.btnTVHome.setOnClickListener {
            getTVs()
        }
//        adapter = HomePageAdapter()
//
//        getMovies()
//
//        binding.btnMoviesHome.setOnClickListener {
//            getMovies()
//        }
//        binding.btnTVHome.setOnClickListener {
//            getTVs()
//        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getMovies() {
        lifecycleScope.launchWhenCreated {
            adapter.loadStateFlow.collect {
                val state = it.refresh
                binding.pbHome.isVisible = state is LoadState.Loading
                binding.tvNoInternet.isVisible = state is LoadState.Error
            }
        }
        lifecycleScope.launchWhenStarted {
            viewModel.movieList.collectLatest {
                binding.apply {
                    binding.tvNoInternet.gone()
                    pbHome.gone()
                    rvHomeResults.adapter = adapter
                }
                adapter.submitData(it)
            }
        }
    }

    private fun getTVs() {
        lifecycleScope.launchWhenCreated {
            adapter.loadStateFlow.collect {
                val state = it.refresh
                binding.pbHome.isVisible = state is LoadState.Loading
                binding.tvNoInternet.isVisible = state is LoadState.Error
            }
        }
//        lifecycleScope.launchWhenCreated {
//            adapter.loadStateFlow.collect {
//                val state = it.refresh
//                if( state is LoadState.Error) {
//                    binding.tvNoInternet.visible()
//                }
//            }
//        }
        lifecycleScope.launchWhenStarted {
            viewModel.tvList.collectLatest {
                binding.apply {
                    binding.tvNoInternet.gone()
                    pbHome.gone()
                    rvHomeResults.adapter = adapter
                }
                adapter.submitData(it)
            }
        }
    }

//    private fun getMovies() {
//        viewModel.getMovieList()
//        viewModel.movieState.observe(viewLifecycleOwner) {
//            when(it) {
//                is UIStates.SUCCESS -> {
//                    binding.pbHome.gone()
//                    binding.rvHomeResults.visible()
//                    binding.rvHomeResults.adapter = adapter
//                    adapter.differ.submitList(it.movieResponse.list)
//                }
//                is UIStates.FAILURE -> {
//
//                }
//                else -> {
//                    binding.rvHomeTV.gone()
//                    binding.pbHome.visible()
//                }
//            }
//        }
//    }

//    private fun getTVs() {
//        viewModel.getTVList()
//        viewModel.tvState.observe(viewLifecycleOwner) {
//            when(it) {
//                is UIStates.SUCCESS -> {
//                    binding.pbHome.gone()
//                    binding.rvHomeTV.visible()
//                    binding.rvHomeTV.adapter = adapter
//                    adapter.differ.submitList(it.movieResponse.list)
//                }
//                is UIStates.FAILURE -> {
//
//                }
//                else -> {
//                    binding.rvHomeResults.gone()
//                    binding.pbHome.visible()
//                }
//            }
//        }
//    }
}