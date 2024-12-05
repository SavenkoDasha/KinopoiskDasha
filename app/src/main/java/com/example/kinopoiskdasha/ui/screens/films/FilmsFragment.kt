package com.example.kinopoiskdasha.ui.screens.films
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
import com.example.kinopoiskdasha.databinding.FragmentFilmsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
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
                viewModel.handleEvent(FilmsEvent.OnSortClicked)
            }

            filmsRecyclerView.layoutManager =
                object : LinearLayoutManager(context) {
                    override fun supportsPredictiveItemAnimations() = false
                }
            filmsRecyclerView.adapter = filmsAdapter

            filmsRecyclerView.setOnScrollChangeListener { _, _, _, _, _ ->
                val pos = (filmsRecyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                viewModel.handleEvent(FilmsEvent.OnScrollPositionChanged(pos))
            }

            val dividerItemDecoration = DividerItemDecoration(context, RecyclerView.VERTICAL)
            dividerItemDecoration.setDrawable(resources.getDrawable(R.drawable.divider_drawable))
            filmsRecyclerView.addItemDecoration(dividerItemDecoration)
        }

        lifecycleScope.launch {
            viewModel.uiState.collect { state ->
                filmsAdapter.saveData(state.listItems)
            }
        }
    }
    private fun handleLabels(label: FilmsLabel) {
        when (label) {
            FilmsLabel.OnNavigateToLogin -> {
                findNavController().navigate(R.id.action_filmsFragment_to_loginFragment)
            }
            is FilmsLabel.Exception -> {
                Toast.makeText(context, label.message, Toast.LENGTH_SHORT).show()
            }
            else -> {}
        }
    }
}
