package com.example.kinopoiskdasha.ui.screens.movies.detail

import androidx.recyclerview.widget.DiffUtil
import com.example.kinopoiskdasha.ui.model.DetailListItem
import com.example.kinopoiskdasha.ui.screens.movies.detailAdapterDelegate
import com.example.kinopoiskdasha.ui.screens.movies.mockImageAdapterDelegate
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter

class DetailAdapter : AsyncListDifferDelegationAdapter<DetailListItem>(diffUtil){
    init {
        delegatesManager.addDelegate(detailAdapterDelegate())
        delegatesManager.addDelegate(mockImageAdapterDelegate())
    }
}

private val diffUtil = object : DiffUtil.ItemCallback<DetailListItem>() {
    override fun areItemsTheSame(oldItem: DetailListItem, newItem: DetailListItem): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: DetailListItem, newItem: DetailListItem): Boolean {
        return oldItem == newItem
    }
}
