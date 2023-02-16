package ru.ivos.quantron_movies.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.ivos.quantron_movies.R
import ru.ivos.quantron_movies.databinding.FragmentSearchBinding
import ru.ivos.quantron_movies.presentation.viewmodels.SearchViewModel
import ru.ivos.quantron_movies.presentation.adapters.SearchAndFavoritesAdapter
import ru.ivos.quantron_movies.utils.UIStates
import ru.ivos.quantron_movies.utils.gone
import ru.ivos.quantron_movies.utils.visible

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding: FragmentSearchBinding
        get() = _binding ?: throw RuntimeException("Binding is empty")

    private val viewModel by viewModels<SearchViewModel>()

    private lateinit var adapter: SearchAndFavoritesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = SearchAndFavoritesAdapter()

        binding.svSearch.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    viewModel.getMovieList(query)
                    observeViewModel()
                }
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return true
            }
        })

        adapter.setOnItemClickListener {
            findNavController().navigate(
                R.id.action_searchFragment_to_detailsFragment,
                bundleOf(Pair("details", it))
            )
        }

        observeViewModel()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun observeViewModel() {
        viewModel.movieState.observe(viewLifecycleOwner) {
            when (it) {
                is UIStates.SUCCESS -> {
                    binding.pbSearch.gone()
                    binding.rvSearchResults.visible()
                    binding.rvSearchResults.adapter = adapter
                    adapter.differ.submitList(it.movieResponse.list)

                    if(it.movieResponse.list.isEmpty()) {
                        binding.tvSearchText.text = requireContext().getString(R.string.no_results)
                    } else {
                        binding.tvSearchText.gone()
                    }
                }
                is UIStates.FAILURE -> {
                    binding.pbSearch.gone()
                    binding.tvSearchText.text = requireContext().getString(R.string.no_internet_connection)
                }
                else -> {
                    binding.rvSearchResults.gone()
                    binding.pbSearch.visible()
                }
            }
        }
    }

}