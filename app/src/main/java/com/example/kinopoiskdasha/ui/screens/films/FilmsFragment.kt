package com.example.kinopoiskdasha.ui.screens.films
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.kinopoiskdasha.R
import com.example.kinopoiskdasha.databinding.FragmentFilmsBinding
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch
import timber.log.Timber

class FilmsFragment : Fragment(R.layout.fragment_films) {
    private val viewBinding: FragmentFilmsBinding by viewBinding(FragmentFilmsBinding::bind)
    private val viewModel: FilmsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            viewModel.labels.consumeEach(::handleLabels)
        }

        val filmsAdapter = FilmsAdapter()
        with(viewBinding) {
            logoutBtn.setOnClickListener {
                 viewModel.handleEvent(FilmsEvent.OnLogOutClicked)
            }
            sortBtn.setOnClickListener {
                Timber.tag("Sort").d("on click")
                viewModel.handleEvent(FilmsEvent.OnSortClicked)
            }

            filmsRecyclerView.layoutManager =
                LinearLayoutManager(context)
            filmsRecyclerView.adapter = filmsAdapter

            filmsRecyclerView.setOnScrollChangeListener { _, _, _, _, _ ->
                val pos = (filmsRecyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                viewModel.handleEvent(FilmsEvent.OnScrollPositionChanged(pos))
                Timber.tag("FilmsFragment").d("onViewCreated: %s", pos)
            }
        }

        lifecycleScope.launch {
            viewModel.uiState.collect { state ->
                filmsAdapter.saveData(state.films)
            }
        }

        lifecycleScope.launch {
            viewModel.uiState.collect { state ->
                filmsAdapter.saveData(state.films)
            }
        }
    }
    private fun handleLabels(label: FilmsLabel) {
        when (label) {
            FilmsLabel.OnNavigateToLogin -> {
                findNavController().navigate(R.id.action_filmsFragment_to_loginFragment)
            }
            else -> {}
        }
    }
}
