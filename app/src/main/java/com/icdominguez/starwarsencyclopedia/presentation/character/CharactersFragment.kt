package com.icdominguez.starwarsencyclopedia.presentation.character

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
import com.icdominguez.starwarsencyclopedia.data.common.Outcome
import com.icdominguez.starwarsencyclopedia.databinding.FragmentCharactersBinding
import com.icdominguez.starwarsencyclopedia.domain.model.Item
import com.icdominguez.starwarsencyclopedia.presentation.adapters.GenericAdapter
import com.icdominguez.starwarsencyclopedia.presentation.adapters.ItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class CharactersFragment : Fragment() {

    private lateinit var binding: FragmentCharactersBinding
    private lateinit var adapter: GenericAdapter
    private val characterViewModel: CharacterViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentCharactersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpView()
    }

    private fun setUpView() {
        characterViewModel.getCharacters()
        Timber.i("Getting all characters ...")

        characterViewModel.characterDetailListObserver.observe(
            viewLifecycleOwner,
            { response ->
                when(response) {
                    is Outcome.Success -> {
                        Timber.i("Characters got from server: ${response.data}")
                        adapter = GenericAdapter(requireActivity(), response.data as ArrayList<Item>, onCharacterClicked)
                        with(binding) {
                            recyclerViewCharacters.adapter = adapter
                            recyclerViewCharacters.addItemDecoration(ItemDecoration(10))
                            recyclerViewCharacters.apply {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                    layoutManager = GridLayoutManager(requireActivity(), 2)
                                }
                            }
                            progressBarCharacters.visibility = View.GONE

                            searchViewCharacters.setOnClickListener {
                                searchViewCharacters.isIconified = false
                            }

                            searchViewCharacters.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
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

                            imageViewCharactersBack.setOnClickListener {
                                findNavController().popBackStack()
                            }
                        }
                    }
                    is Outcome.Failure -> {
                        Timber.i("There was an error getting the characters: ${response.exception}")
                        binding.progressBarCharacters.visibility = View.GONE
                    }
                }
            }

        )
    }

    private val onCharacterClicked: (name: String) -> Unit = { characterName ->
        Timber.i("User clicked on $characterName")
        val action = CharactersFragmentDirections.actionCharactersFragmentToCharacterInfoFragment(characterName)
        findNavController().navigate(action)
    }
}
