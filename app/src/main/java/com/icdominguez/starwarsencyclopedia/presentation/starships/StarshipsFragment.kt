package com.icdominguez.starwarsencyclopedia.presentation.starships

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
import com.icdominguez.starwarsencyclopedia.databinding.FragmentStarshipsBinding
import com.icdominguez.starwarsencyclopedia.domain.model.Item
import com.icdominguez.starwarsencyclopedia.presentation.adapters.GenericAdapter
import com.icdominguez.starwarsencyclopedia.presentation.adapters.ItemDecoration
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StarshipsFragment : Fragment() {

    private lateinit var binding: FragmentStarshipsBinding
    private lateinit var adapter: GenericAdapter
    private val starshipsViewModel: StarshipsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentStarshipsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpView()
    }

    private fun setUpView() {
        starshipsViewModel.getAllStarhips()

        starshipsViewModel.starshipListObserver.observe(
            viewLifecycleOwner,
            { starshipList ->
                adapter = GenericAdapter(requireActivity(), starshipList as ArrayList<Item>, onStarshipClicked)
                with(binding) {
                    recyclerViewStarships.adapter = adapter
                    recyclerViewStarships.addItemDecoration(ItemDecoration(10))
                    recyclerViewStarships.apply {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            layoutManager = GridLayoutManager(requireActivity(), 2)
                        }
                    }
                    progressBarStarships.visibility = View.GONE

                    searchViewStarships.setOnClickListener {
                        searchViewStarships.isIconified = false
                    }

                    searchViewStarships.setOnQueryTextListener(object :
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

                    imageViewStarshipsBack.setOnClickListener {
                        findNavController().popBackStack()
                    }
                }
            }
        )
    }

    private val onStarshipClicked: (name: String) -> Unit = { starshipName ->
        val action = StarshipsFragmentDirections.actionStarshipsFragmentToStarshipInfoFragment(starshipName)
        findNavController().navigate(action)
    }
}
