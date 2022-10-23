package com.farroos.movieapp_newfeatured.ui.home;

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.farroos.movieapp_newfeatured.data.remote.home.Movie
import com.farroos.movie.utils.urlImage
import com.farroos.movieapp_newfeatured.R
import com.farroos.movieapp_newfeatured.databinding.ItemMovieBinding


class HomeAdapter : ListAdapter<Movie, HomeAdapter.HomeViewHolder>(DiffCallBack()) {
    class HomeViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val binding = ItemMovieBinding.bind(view)
        fun bind(nowPlaying: Movie){
            binding.apply {
                binding.txtJudulFilm.text = nowPlaying.title
                binding.txtRate.text = nowPlaying.voteAverage.toString()
                Glide.with(binding.root).load(urlImage + nowPlaying.posterPath)
                    .error(R.drawable.ic_broken)
                    .into(binding.imgFilm)
                root.setOnClickListener {
                    val id = HomeFragmentDirections.actionHomeFragmentToDetailFragment(nowPlaying.id!!)
                    it.findNavController().navigate(id)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        return HomeViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false))
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val data = getItem(position)
        holder.bind(data)
    }

}


class DiffCallBack : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem == newItem
    }

}