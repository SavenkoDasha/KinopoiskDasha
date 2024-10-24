package com.example.kinopoiskdasha.ui.screens.films

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.kinopoiskdasha.R
import com.example.kinopoiskdasha.databinding.FragmentFilmsBinding

class FilmsFragment : Fragment(R.layout.fragment_films) {
    private val viewBinding: FragmentFilmsBinding by viewBinding(FragmentFilmsBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}
