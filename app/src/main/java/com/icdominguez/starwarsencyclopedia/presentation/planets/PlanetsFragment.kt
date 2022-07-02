package com.icdominguez.starwarsencyclopedia.presentation.planets

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.icdominguez.starwarsencyclopedia.databinding.FragmentPlanetsBinding
import com.icdominguez.starwarsencyclopedia.domain.model.Item
import com.icdominguez.starwarsencyclopedia.presentation.adapters.GenericAdapter
import com.icdominguez.starwarsencyclopedia.presentation.adapters.ItemDecoration
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlanetsFragment : Fragment() {

    private lateinit var binding: FragmentPlanetsBinding
    private lateinit var adapter: GenericAdapter
    private val planetViewModel: PlanetViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentPlanetsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpView()
    }

    private fun setUpView() {
        planetViewModel.getPlanets()

        planetViewModel.planetsListObserver.observe(
            viewLifecycleOwner,
            { planetList ->
                adapter = GenericAdapter(requireActivity(), planetList as ArrayList<Item>, onPlanetClicked)
                with(binding) {
                    recyclerViewPlanets.adapter = adapter
                    recyclerViewPlanets.addItemDecoration(ItemDecoration(10))
                    recyclerViewPlanets.apply {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            layoutManager = GridLayoutManager(requireActivity(), 2)
                        }
                    }
                    progessBarPlanets.visibility = View.GONE

                    searchViewPlanets.setOnClickListener {
                        searchViewPlanets.isIconified = false
                    }

                    searchViewPlanets.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                        override fun onQueryTextSubmit(p0: String?): Boolean {
                            return true
                        }

                        override fun onQueryTextChange(textFilter: String?): Boolean {
                            textFilter?.let {
                                adapter.filter.filter(it)
                            }
                            return true
                        }
                    })

                    imageViewPlanetsBack.setOnClickListener {
                        findNavController().popBackStack()
                    }
                }
            }
        )
    }

    private val onPlanetClicked: (name: String) -> Unit = { planetName ->
        val action = PlanetsFragmentDirections.actionPlanetsFragmentToPlanetInfoFragment(planetName)
        findNavController().navigate(action)
    }
}
