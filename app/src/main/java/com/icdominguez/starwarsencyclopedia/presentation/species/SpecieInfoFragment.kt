package com.icdominguez.starwarsencyclopedia.presentation.species

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.icdominguez.starwarsencyclopedia.R
import com.icdominguez.starwarsencyclopedia.data.common.checkIfIsEmpty
import com.icdominguez.starwarsencyclopedia.databinding.FragmentSpecieInfoBinding
import com.icdominguez.starwarsencyclopedia.domain.model.detail.CharacterDetail
import com.icdominguez.starwarsencyclopedia.domain.model.detail.FilmDetail
import com.icdominguez.starwarsencyclopedia.presentation.adapters.HorizontalAdapter
import dagger.hilt.android.AndroidEntryPoint
import java.util.ArrayList

@AndroidEntryPoint
class SpecieInfoFragment : DialogFragment() {

    private lateinit var binding: FragmentSpecieInfoBinding
    private val viewModel: SpeciesViewModel by viewModels()
    private val args: SpecieInfoFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.FullScreenDialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentSpecieInfoBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpView()
    }

    private fun setUpView() {
        args.specie?.let {
            viewModel.getSpecieByName(it)

            viewModel.specieObserver.observe(
                viewLifecycleOwner,
                { specieInfo ->
                    with(binding) {

                        if (specieInfo.photo.isNotEmpty()) {
                            Glide.with(requireActivity()).load(specieInfo.photo).into(imageViewSpecie)
                        } else {
                            Glide.with(requireActivity()).load(R.drawable.no_picture).into(imageViewSpecie)
                        }

                        textViewSpecieName.text = specieInfo.name.checkIfIsEmpty()
                        textViewClassificationValue.text = specieInfo.classification.checkIfIsEmpty()
                        textViewDesignationValue.text = specieInfo.designation.checkIfIsEmpty()
                        textViewLanguageValue.text = specieInfo.language.checkIfIsEmpty()
                        textViewAvgLifespanValue.text = specieInfo.avgLifespan.checkIfIsEmpty()
                        textViewAvgHeightValue.text = specieInfo.avgHeight.checkIfIsEmpty()
                        textViewHairColorsValue.text = specieInfo.hairColor.toString()
                        textViewSkinColorsValue.text = specieInfo.skinColor.toString()
                        textViewEyeColorsValue.text = specieInfo.eyeColor.toString()

                        progressBarLoadingSpecie.visibility = View.GONE
                        showView()

                        setUpRecyclerViewFilms(specieInfo.relatedFilms)
                        setUpRecyclerViewCharacters(specieInfo.relatedCharacters)

                        imageViewSpecieInfoBack.setOnClickListener {
                            findNavController().popBackStack()
                        }

                    }
                }
            )
        }
    }

    private fun setUpRecyclerViewCharacters(relatedCharacters: ArrayList<CharacterDetail>) {
        if (relatedCharacters.isNotEmpty()) {
            with(binding){
                recyclerViewCharacters.adapter = HorizontalAdapter(requireActivity(), relatedCharacters, onCharacterClicked)
                apply {
                    recyclerViewCharacters.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
                }
                recyclerViewCharacters.visibility = View.VISIBLE

                imageViewExpandCharacters.setOnClickListener {
                    recyclerViewCharacters.visibility = if(recyclerViewCharacters.visibility == View.GONE) {
                        View.VISIBLE
                    } else {
                        View.GONE
                    }
                }
            }
        }
    }

    private fun setUpRecyclerViewFilms(relatedFilms: ArrayList<FilmDetail>) {
        if (relatedFilms.isNotEmpty()) {
            with(binding){
                recyclerViewFilms.adapter = HorizontalAdapter(requireActivity(), relatedFilms, onFilmClicked)
                apply {
                    recyclerViewFilms.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
                }
                recyclerViewFilms.visibility = View.VISIBLE

                imageViewExpandFilms.setOnClickListener {
                    recyclerViewFilms.visibility = if(recyclerViewFilms.visibility == View.GONE) {
                        View.VISIBLE
                    } else {
                        View.GONE
                    }
                }
            }
        }
    }

    private fun showView() {
        with(binding) {
            imageViewSpecie.visibility = View.VISIBLE
            textViewSpecieName.visibility = View.VISIBLE
            viewFilmsSeparator.visibility = View.VISIBLE
            textViewFilms.visibility = View.VISIBLE
            imageViewExpandFilms.visibility = View.VISIBLE
            recyclerViewFilms.visibility = View.VISIBLE
            viewSeparatorCharacters.visibility = View.VISIBLE
            textViewCharacters.visibility = View.VISIBLE
            imageViewExpandCharacters.visibility = View.VISIBLE
            recyclerViewCharacters.visibility = View.VISIBLE
            viewFinalSeparator.visibility = View.VISIBLE

            textViewAvgHeightLabel.visibility = View.VISIBLE
            textViewAvgLifespanLabel.visibility = View.VISIBLE
            textViewClassificationLabel.visibility = View.VISIBLE
            textViewDesignationLabel.visibility = View.VISIBLE
            textViewEyeColorsLabel.visibility = View.VISIBLE
            textViewHairColorsLabel.visibility = View.VISIBLE
            textViewLanguageLabel.visibility = View.VISIBLE
            textViewSkinColorsLabel.visibility = View.VISIBLE

            imageViewSpecieInfoBack.visibility = View.VISIBLE
        }
    }

    private val onFilmClicked: (film: FilmDetail) -> Unit = { film ->
        val action = SpecieInfoFragmentDirections.actionSpecieInfoFragmentToFilmInfoFragment(film.name)
        findNavController().navigate(action)
    }

    private val onCharacterClicked: (film: CharacterDetail) -> Unit = { character ->
        val action = SpecieInfoFragmentDirections.actionSpecieInfoFragmentToCharacterInfoFragment(character.name)
        findNavController().navigate(action)
    }
}
