package com.icdominguez.starwarsencyclopedia.presentation.vehicles

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
import com.icdominguez.starwarsencyclopedia.databinding.FragmentVehiclesBinding
import com.icdominguez.starwarsencyclopedia.domain.model.Item
import com.icdominguez.starwarsencyclopedia.presentation.adapters.GenericAdapter
import com.icdominguez.starwarsencyclopedia.presentation.adapters.ItemDecoration
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VehiclesFragment : Fragment() {

    private lateinit var binding: FragmentVehiclesBinding
    private lateinit var adapter: GenericAdapter
    private val vehiclesViewModel: VehiclesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentVehiclesBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpView()
    }

    private fun setUpView() {

        vehiclesViewModel.getAllVehicles()

        vehiclesViewModel.getAllVehiclesStatus.observe(
            viewLifecycleOwner,
            { vehicles ->
                adapter = GenericAdapter(requireActivity(), vehicles as ArrayList<Item>, onVehicleClicked)
                with(binding) {
                    recyclerViewVehicles.adapter = adapter
                    recyclerViewVehicles.addItemDecoration(ItemDecoration(10))
                    recyclerViewVehicles.apply {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            layoutManager = GridLayoutManager(requireActivity(), 2)
                        }
                    }
                    progressBarVehicles.visibility = View.GONE

                    searchViewVehicles.setOnClickListener {
                        searchViewVehicles.isIconified = false
                    }

                    imageViewVehiclesGoBack.setOnClickListener {
                        findNavController().popBackStack()
                    }

                    searchViewVehicles.setOnQueryTextListener(object :
                        SearchView.OnQueryTextListener {
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
                }
            }
        )
    }

    private val onVehicleClicked: (name: String) -> Unit = { name ->
        val action = VehiclesFragmentDirections.actionVehiclesFragmentToVehicleInfoFragment(name)
        findNavController().navigate(action)
    }
}
