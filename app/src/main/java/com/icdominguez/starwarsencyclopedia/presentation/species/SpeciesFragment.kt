package com.icdominguez.starwarsencyclopedia.presentation.species

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
import com.icdominguez.starwarsencyclopedia.databinding.FragmentSpeciesBinding
import com.icdominguez.starwarsencyclopedia.domain.model.Item
import com.icdominguez.starwarsencyclopedia.presentation.adapters.GenericAdapter
import com.icdominguez.starwarsencyclopedia.presentation.adapters.ItemDecoration
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SpeciesFragment : Fragment() {

    private lateinit var binding: FragmentSpeciesBinding
    private lateinit var adapter: GenericAdapter
    private val speciesViewModel: SpeciesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentSpeciesBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpView()
    }

    private fun setUpView() {
        speciesViewModel.getAllSpecies()

        speciesViewModel.speciesListObserver.observe(
            viewLifecycleOwner,
            { specieList ->
                adapter = GenericAdapter(requireActivity(), specieList as ArrayList<Item>, onSpecieClicked)
                with(binding) {
                    recyclerViewSpecies.adapter = adapter
                    recyclerViewSpecies.addItemDecoration(ItemDecoration(10))
                    recyclerViewSpecies.apply {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            layoutManager = GridLayoutManager(requireActivity(), 2)
                        }
                    }
                    progressBarSpecies.visibility = View.GONE

                    searchViewSpecies.setOnClickListener {
                        searchViewSpecies.isIconified = false
                    }

                    searchViewSpecies.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
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

                    imageViewSpecieBack.setOnClickListener {
                        findNavController().popBackStack()
                    }
                }
            }
        )
    }

    private val onSpecieClicked: (name: String) -> Unit = { specieName ->
        val action = SpeciesFragmentDirections.actionSpeciesFragmentToSpecieInfoFragment(specieName)
        findNavController().navigate(action)
    }
}
