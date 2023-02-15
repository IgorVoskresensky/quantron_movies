package ru.ivos.quantron_movies.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.ivos.quantron_movies.data.models.movie.MovieDetails
import ru.ivos.quantron_movies.databinding.ItemMovieHomeBinding
import ru.ivos.quantron_movies.utils.Constants

class HomePagingAdapter : PagingDataAdapter<MovieDetails, HomePagingAdapter.HomePagingViewHolder>(differCallback) {

    inner class HomePagingViewHolder(
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

    override fun onBindViewHolder(holder: HomePagingViewHolder, position: Int) {
        holder.bind(getItem(position)!!)
        holder.setIsRecyclable(false)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomePagingViewHolder {
        return HomePagingViewHolder(
            ItemMovieHomeBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    companion object {

        private val differCallback = object : DiffUtil.ItemCallback<MovieDetails>() {
            override fun areItemsTheSame(oldItem: MovieDetails, newItem: MovieDetails): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: MovieDetails, newItem: MovieDetails): Boolean {
                return oldItem == newItem
            }
        }
    }

    private var onItemClickListener: ((MovieDetails) -> Unit)? = null

    fun setOnItemClickListener(listener: (MovieDetails) -> Unit) {
        onItemClickListener = listener
    }
}