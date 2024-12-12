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
import com.example.kinopoiskdasha.databinding.YearItemBinding
import com.example.kinopoiskdasha.ui.model.ListItem
import com.example.kinopoiskdasha.ui.model.MovieUi
import com.example.kinopoiskdasha.ui.model.YearItem

class FilmsAdapter : RecyclerView.Adapter<FilmsAdapter.ViewHolder>() {

    private val diffUtil = object : DiffUtil.ItemCallback<ListItem>() {
        override fun areItemsTheSame(oldItem: ListItem, newItem: ListItem): Boolean {
            return if (oldItem is MovieUi && newItem is MovieUi) {
                oldItem.id == newItem.id
            } else if (oldItem is YearItem && newItem is YearItem) {
                oldItem.year == newItem.year
            } else {
                false
            }
        }

        override fun areContentsTheSame(oldItem: ListItem, newItem: ListItem): Boolean {
            return oldItem == newItem
        }
    }


    private val dataSet = AsyncListDiffer(this, diffUtil)

    sealed class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        abstract fun bind(item: ListItem)

        class YearViewHolder(view: View) : ViewHolder(view) {
            private val binding: YearItemBinding by viewBinding(YearItemBinding::bind)

            override fun bind(item: ListItem) {
                if (item !is YearItem) return

                with(binding) {
                    year.text = item.year
                }
            }
        }

        class MovieViewHolder(view: View) : ViewHolder(view) {
            private val binding: FilmsItemBinding by viewBinding(FilmsItemBinding::bind)

            override fun bind(item: ListItem) {
                if (item !is MovieUi) return

                with(binding) {
                    Glide.with(root.context)
                        .load(item.imageUrl)
                        .placeholder(R.drawable.baseline_downloading_24)
                        .into(ivFilm)
                    tvFilmName.text = item.nameOriginal
                    tvFilmGenres.text = item.genres
                    tvFilmYearAndCountry.text = item.filmYearAndCountry
                    tvFilmRating.text = item.ratingKinopoisk
                }
            }
        }
    }
    override fun getItemViewType(position: Int): Int {
        return when (dataSet.currentList[position]) {
            is MovieUi -> MOVIE_ITEM
            is YearItem -> YEAR_ITEM
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = when (viewType) {
            MOVIE_ITEM -> LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.films_item, viewGroup, false)

            YEAR_ITEM -> LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.year_item, viewGroup, false)

            else -> throw IllegalStateException("Incorrect item's type")
        }

        return when (viewType) {
            MOVIE_ITEM -> ViewHolder.MovieViewHolder(view)
            YEAR_ITEM -> ViewHolder.YearViewHolder(view)
            else -> throw IllegalStateException("Incorrect item's type")
        }
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val item = dataSet.currentList[position]
        viewHolder.bind(item)
    }

    override fun getItemCount() = dataSet.currentList.size

    fun saveData(movies: List<ListItem>) {
        dataSet.submitList(movies)
    }

    companion object {
        private const val YEAR_ITEM = 0
        private const val MOVIE_ITEM = 1
    }
}
