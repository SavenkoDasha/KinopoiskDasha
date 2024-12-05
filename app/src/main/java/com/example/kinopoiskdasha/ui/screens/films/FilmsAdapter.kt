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
import com.example.kinopoiskdasha.ui.model.ListItem
import com.example.kinopoiskdasha.ui.model.MovieUi
import com.example.kinopoiskdasha.ui.model.YearItem

class FilmsAdapter: RecyclerView.Adapter<FilmsAdapter.ViewHolder>() {

    private val diffUtil = object : DiffUtil.ItemCallback<ListItem>() {
        override fun areItemsTheSame(oldItem: ListItem, newItem: ListItem): Boolean {
            return if (oldItem is MovieUi && newItem is MovieUi){
                oldItem.id == newItem.id
            } else if (oldItem is YearItem && newItem is YearItem){
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

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val binding : FilmsItemBinding by viewBinding(FilmsItemBinding::bind)
    }

    override fun getItemViewType(position: Int): Int {
        return when (dataSet.currentList[position]) {
            is MovieUi -> MOVIE_ITEM
            is YearItem -> YEAR_ITEM
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.films_item, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder,  position: Int) {
//        with(dataSet.currentList[position]) {
//            with(viewHolder.binding) {
//                Glide.with(root.context)
//                    .load(imageUrl)
//                    .placeholder(R.drawable.baseline_downloading_24)
//                    .into(ivFilm)
//                tvFilmName.text = nameOriginal
//                tvFilmGenres.text = genres
//                tvFilmYearAndCountry.text = filmYearAndCountry
//                tvFilmRating.text = ratingKinopoisk
//            }
//        }
        TODO()
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
