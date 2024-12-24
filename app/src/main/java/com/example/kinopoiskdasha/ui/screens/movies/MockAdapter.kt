package com.example.kinopoiskdasha.ui.screens.movies

import androidx.recyclerview.widget.DiffUtil
import com.example.kinopoiskdasha.ui.model.ListItem
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter

class MockAdapter : AsyncListDifferDelegationAdapter<ListItem>(diffUtil){
    init {
        delegatesManager.addDelegate(mockAdapterDelegate())
    }
}

private val diffUtil = object : DiffUtil.ItemCallback<ListItem>() {
    override fun areItemsTheSame(oldItem: ListItem, newItem: ListItem): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: ListItem, newItem: ListItem): Boolean {
        return oldItem == newItem
    }
}
