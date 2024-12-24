package com.example.kinopoiskdasha.ui.screens.movies

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.kinopoiskdasha.R
import com.example.kinopoiskdasha.databinding.FragmentMoviesBinding
import com.example.kinopoiskdasha.ui.model.MockItem
import com.example.kinopoiskdasha.ui.model.RecyclerItem
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MoviesFragment : Fragment(R.layout.fragment_movies) {
    private val viewBinding: FragmentMoviesBinding by viewBinding(FragmentMoviesBinding::bind)
    private val viewModel: MoviesViewModel by viewModels()

    private val moviesAdapter = MoviesAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            viewModel.labels.consumeEach(::handleLabels)
        }

        with(viewBinding) {
            logoutBtn.setOnClickListener {
                viewModel.handleEvent(MoviesEvent.OnLogOutClicked)
            }
            sortBtn.setOnClickListener {
                viewModel.handleEvent(MoviesEvent.OnSortClicked)
            }

            //for movies
            moviesRecyclerView.layoutManager =
                object : LinearLayoutManager(context) {
                    override fun supportsPredictiveItemAnimations() = false
                }
            moviesRecyclerView.adapter = moviesAdapter

            moviesRecyclerView.setOnScrollChangeListener { _, _, _, _, _ ->
                val pos = (moviesRecyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                viewModel.handleEvent(MoviesEvent.OnScrollPositionChanged(pos))
            }

            val dividerItemDecoration = DividerItemDecoration(context, RecyclerView.VERTICAL)
            dividerItemDecoration.setDrawable(resources.getDrawable(R.drawable.divider_drawable))
            moviesRecyclerView.addItemDecoration(dividerItemDecoration)
        }

        lifecycleScope.launch {
            viewModel.uiState.collect { state ->
                moviesAdapter.items = state.listItems
            }
        }
    }

    private fun handleLabels(label: MoviesLabel) {
        when (label) {
            MoviesLabel.OnNavigateToLogin -> {
                findNavController().navigate(R.id.action_MoviesFragment_to_loginFragment)
            }
            is MoviesLabel.Exception -> {
                Toast.makeText(context, label.message, Toast.LENGTH_SHORT).show()
            }
            else -> {}
        }
    }
}
