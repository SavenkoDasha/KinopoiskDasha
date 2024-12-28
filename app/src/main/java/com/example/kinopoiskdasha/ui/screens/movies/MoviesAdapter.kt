package com.example.kinopoiskdasha.ui.screens.movies

import androidx.recyclerview.widget.DiffUtil
import com.example.kinopoiskdasha.ui.model.ListItem
import com.example.kinopoiskdasha.ui.model.MockItem
import com.example.kinopoiskdasha.ui.model.MovieUi
import com.example.kinopoiskdasha.ui.model.RecyclerItem
import com.example.kinopoiskdasha.ui.model.YearItem
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter

class MoviesAdapter(onMovieClick: (Int) -> Unit) : AsyncListDifferDelegationAdapter<ListItem>(diffUtil) {
    init {
        delegatesManager.addDelegate(yearAdapterDelegate())
        delegatesManager.addDelegate(movieAdapterDelegate(onMovieClick))
        delegatesManager.addDelegate(recyclerAdapterDelegate())
    }
}

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
