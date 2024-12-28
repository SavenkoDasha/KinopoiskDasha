package com.example.kinopoiskdasha.ui.screens.movies.detail

import android.os.Bundle
import android.os.Parcelable
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.example.kinopoiskdasha.R
import com.example.kinopoiskdasha.databinding.FragmentDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieDetailsArgs(val id: Int) : Parcelable

@AndroidEntryPoint
class DetailFragment : Fragment(R.layout.fragment_detail) {
    private val viewBinding: FragmentDetailBinding by viewBinding(FragmentDetailBinding::bind)
    private val viewModel: DetailViewModel by viewModels()

    private val detailAdapter = DetailAdapter()

    companion object {
        const val ARGS_KEY = "ARGS_KEY"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            viewModel.labels.consumeEach(::handleLabels)
        }

        with(viewBinding) {
            ivBack.setOnClickListener {
                viewModel.handleEvent(DetailEvent.OnBackClicked)
            }

            rvFrames.layoutManager =
                object : LinearLayoutManager(context) {
                    override fun supportsPredictiveItemAnimations() = false
                }
            rvFrames.adapter = detailAdapter
        }

        lifecycleScope.launch {
            viewModel.uiState.collect { state ->
                detailAdapter.items = state.listItems

                with(viewBinding) {
                    tvMovieName.text = state.details?.nameOriginal
                    tvDescriptionText.text = state.details?.description
                    tvRating.text = state.details?.ratingKinopoisk
                    tvMovieYearAndCountry.text = state.details?.movieYearAndCountry

                    Glide.with(root.context)
                        .load(state.details?.coverUrl)
                        .placeholder(R.drawable.baseline_downloading_24)
                        .into(ivMovie)
                }
            }
        }
    }


    private fun handleLabels(label: DetailLabel) {
        when (label) {
            DetailLabel.OnNavigateToMoviesScreen -> {
                findNavController().popBackStack()
            }

            is DetailLabel.Exception -> {
                Toast.makeText(context, label.message, Toast.LENGTH_SHORT).show()
            }
        }
    }
}
