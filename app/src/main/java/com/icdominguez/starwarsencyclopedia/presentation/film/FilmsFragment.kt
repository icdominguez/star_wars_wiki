package com.icdominguez.starwarsencyclopedia.presentation.film

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.icdominguez.starwarsencyclopedia.databinding.FragmentFilmsBinding
import com.icdominguez.starwarsencyclopedia.domain.model.Item
import com.icdominguez.starwarsencyclopedia.presentation.adapters.GenericAdapter
import com.icdominguez.starwarsencyclopedia.presentation.adapters.ItemDecoration
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FilmsFragment : Fragment() {

    private lateinit var binding: FragmentFilmsBinding
    private lateinit var adapter: GenericAdapter
    private val filmsViewModel: FilmsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentFilmsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpView()
    }

    private fun setUpView() {
        filmsViewModel.getFilms()

        filmsViewModel.filmListObserver.observe(
            viewLifecycleOwner,
            { filmList ->
                adapter = GenericAdapter(requireActivity(), filmList as ArrayList<Item>, onFilmClicked)
                with(binding) {
                    recyclerViewFilms.adapter = adapter
                    recyclerViewFilms.addItemDecoration(ItemDecoration(10))
                    recyclerViewFilms.apply {
                        layoutManager = GridLayoutManager(requireActivity(), 2)
                    }
                    progressBarFilms.visibility = View.GONE

                    searchViewFilms.setOnClickListener {
                        searchViewFilms.isIconified = false
                    }

                    imageViewFilmsBack.setOnClickListener {
                        findNavController().popBackStack()
                    }

                    searchViewFilms.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
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

    private val onFilmClicked: (name: String) -> Unit = { filmName ->
        val action = FilmsFragmentDirections.actionFilmsFragmentToFilmInfoFragment(filmName)
        findNavController().navigate(action)
    }
}
