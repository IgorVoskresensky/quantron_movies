package ru.ivos.quantron_movies.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.ivos.quantron_movies.data.models.movie.MovieDetails
import ru.ivos.quantron_movies.databinding.ItemMovieHomeBinding
import ru.ivos.quantron_movies.utils.Constants

class SearchAdapter : RecyclerView.Adapter<SearchAdapter.HomePageViewHolder>() {

    inner class HomePageViewHolder(
        private val binding: ItemMovieHomeBinding
        ) : RecyclerView.ViewHolder(binding.root) {

            fun bind(details: MovieDetails) {
                binding.apply {
                    val url = "${Constants.POSTER_MAIN_URL}${details.posterPath}"
                    Glide.with(itemView).load(url).into(ivPoster)
                    if(details.title == "") {
                        tvTitle.text = details.name
                    } else {
                        tvTitle.text = details.title
                    }
                    root.setOnClickListener {
                        onItemClickListener?.let {
                            it(details)
                        }
                    }
                }
            }
        }

    private val differCallback = object : DiffUtil.ItemCallback<MovieDetails>() {
        override fun areItemsTheSame(oldItem: MovieDetails, newItem: MovieDetails): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MovieDetails, newItem: MovieDetails): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomePageViewHolder {
        return HomePageViewHolder(
            ItemMovieHomeBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    override fun onBindViewHolder(holder: HomePageViewHolder, position: Int) {
        val movieDetails = differ.currentList[position]
        holder.bind(movieDetails)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var onItemClickListener: ((MovieDetails) -> Unit)? = null

    fun setOnItemClickListener(listener: (MovieDetails) -> Unit) {
        onItemClickListener = listener
    }
}