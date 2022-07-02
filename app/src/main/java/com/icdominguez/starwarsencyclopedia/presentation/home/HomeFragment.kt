package com.icdominguez.starwarsencyclopedia.presentation.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.icdominguez.starwarsencyclopedia.R
import com.icdominguez.starwarsencyclopedia.data.common.Constants.LOG_TAG
import com.icdominguez.starwarsencyclopedia.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var _binding: FragmentHomeBinding
    private val binding get() = _binding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            cardViewCharacters.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_charactersFragment)
                Log.i(LOG_TAG, "User wants to see Characters")
            }

            cardViewVehicles.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_vehiclesFragment)
            }

            cardViewPlanets.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_planetsFragment)
            }

            cardViewStarships.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_starshipsFragment)
            }

            cardViewSpecies.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_speciesFragment)
            }

            cardViewFilms.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_filmsFragment)
            }
        }
    }
}
