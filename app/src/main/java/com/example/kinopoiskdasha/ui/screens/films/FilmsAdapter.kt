package com.example.kinopoiskdasha.ui.screens.films

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.example.kinopoiskdasha.R
import com.example.kinopoiskdasha.databinding.FilmsItemBinding
import com.example.kinopoiskdasha.ui.model.MovieUi

class FilmsAdapter: RecyclerView.Adapter<FilmsAdapter.ViewHolder>() {

    private val diffUtil = object : DiffUtil.ItemCallback<MovieUi>() {
        override fun areItemsTheSame(oldItem: MovieUi, newItem: MovieUi): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MovieUi, newItem: MovieUi): Boolean {
            return oldItem == newItem
        }
    }

    private val dataSet = AsyncListDiffer(this, diffUtil)

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val binding : FilmsItemBinding by viewBinding(FilmsItemBinding::bind)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.films_item, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder,  position: Int) {
        with(dataSet.currentList[position]) {
            with(viewHolder.binding) {
                Glide.with(root.context)
                    .load(imageUrl)
                    .placeholder(R.drawable.baseline_downloading_24)
                    .into(ivFilm)
                tvFilmName.text = nameOriginal
                tvFilmGenres.text = genres
                tvFilmYearAndCountry.text = filmYearAndCountry
                tvFilmRating.text = ratingKinopoisk
            }
        }

    }

    override fun getItemCount() = dataSet.currentList.size

    fun saveData(movies: List<MovieUi>) {
        dataSet.submitList(movies)
    }
}
