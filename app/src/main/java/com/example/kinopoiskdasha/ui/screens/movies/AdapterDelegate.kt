package com.example.kinopoiskdasha.ui.screens.movies

import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.kinopoiskdasha.R
import com.example.kinopoiskdasha.databinding.ImageItemBinding
import com.example.kinopoiskdasha.databinding.MockImageItemBinding
import com.example.kinopoiskdasha.databinding.MockItemBinding
import com.example.kinopoiskdasha.databinding.MoviesItemBinding
import com.example.kinopoiskdasha.databinding.RecyclerItemBinding
import com.example.kinopoiskdasha.databinding.YearItemBinding
import com.example.kinopoiskdasha.ui.model.DetailListItem
import com.example.kinopoiskdasha.ui.model.ImageItem
import com.example.kinopoiskdasha.ui.model.ListItem
import com.example.kinopoiskdasha.ui.model.MockImageItem
import com.example.kinopoiskdasha.ui.model.MockItem
import com.example.kinopoiskdasha.ui.model.MovieUi
import com.example.kinopoiskdasha.ui.model.RecyclerItem
import com.example.kinopoiskdasha.ui.model.YearItem
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding


fun detailAdapterDelegate() =
    adapterDelegateViewBinding<ImageItem, DetailListItem, ImageItemBinding>(
        { layoutInflater, root -> ImageItemBinding.inflate(layoutInflater, root, false) }
    ) {
        bind {
            with(binding) {
                Glide.with(root.context)
                    .load(item.imageUrl)
                    .placeholder(R.drawable.baseline_downloading_24)
                    .into(ivItemDetail)
            }
        }
    }

fun mockImageAdapterDelegate() =
    adapterDelegateViewBinding<MockImageItem, DetailListItem, MockImageItemBinding>(
        { layoutInflater, root -> MockImageItemBinding.inflate(layoutInflater, root, false) }
    ) {
        bind { binding.tvMockImage.text = item.text }
    }

fun mockAdapterDelegate() = adapterDelegateViewBinding<MockItem, ListItem, MockItemBinding>(
    { layoutInflater, root -> MockItemBinding.inflate(layoutInflater, root, false) }
) {
    bind {
        binding.tvMock.text = item.text
    }
}

fun yearAdapterDelegate() = adapterDelegateViewBinding<YearItem, ListItem, YearItemBinding>(
    { layoutInflater, root -> YearItemBinding.inflate(layoutInflater, root, false) }
) {
    bind {
        binding.year.text = item.year
    }
}

fun movieAdapterDelegate(onClick: (id: Int) -> Unit) =
    adapterDelegateViewBinding<MovieUi, ListItem, MoviesItemBinding>(
        { layoutInflater, root -> MoviesItemBinding.inflate(layoutInflater, root, false) }
    ) {
        itemView.setOnClickListener { onClick(item.id) }

        bind {
            with(binding) {
                Glide.with(root.context)
                    .load(item.imageUrl)
                    .placeholder(R.drawable.baseline_downloading_24)
                    .into(ivMovie)
                tvMovieName.text = item.nameOriginal
                tvMovieGenres.text = item.genres
                tvMovieYearAndCountry.text = item.movieYearAndCountry
                tvMovieRating.text = item.ratingKinopoisk
            }
        }
    }

fun recyclerAdapterDelegate() =
    adapterDelegateViewBinding<RecyclerItem, ListItem, RecyclerItemBinding>(
        { layoutInflater, root ->
            RecyclerItemBinding.inflate(layoutInflater, root, false)
        }
    ) {
        binding.mockRecyclerView.layoutManager = object : LinearLayoutManager(context) {
            override fun supportsPredictiveItemAnimations() = false
        }.apply { orientation = LinearLayoutManager.HORIZONTAL }

        bind {
            with(binding.mockRecyclerView) {
                adapter = MockAdapter().apply { items = item.items }
            }
        }
    }
