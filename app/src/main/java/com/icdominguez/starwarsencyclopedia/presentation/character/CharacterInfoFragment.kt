package com.icdominguez.starwarsencyclopedia.presentation.character

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
import com.icdominguez.starwarsencyclopedia.databinding.FragmentCharacterInfoBinding
import com.icdominguez.starwarsencyclopedia.domain.model.detail.FilmDetail
import com.icdominguez.starwarsencyclopedia.domain.model.detail.StarshipDetail
import com.icdominguez.starwarsencyclopedia.domain.model.detail.VehicleDetail
import com.icdominguez.starwarsencyclopedia.presentation.adapters.HorizontalAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharacterInfoFragment : DialogFragment() {

    private val args: CharacterInfoFragmentArgs by navArgs()
    private val characterViewModel: CharacterViewModel by viewModels()

    private lateinit var binding: FragmentCharacterInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.FullScreenDialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentCharacterInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpView()
    }

    private fun setUpView() {

        args.character?.let {

            characterViewModel.getCharacterByName(it)

            characterViewModel.getCharacterStatus.observe(
                requireActivity(),
                { characterInfo ->

                    setViewVisible()

                    with(binding) {
                        if (characterInfo.photo.isNotEmpty()) {
                            Glide.with(requireActivity()).load(characterInfo.photo)
                                .into(imageViewCharacter)
                        } else {
                            Glide.with(requireActivity()).load(R.drawable.no_picture)
                                .into(imageViewCharacter)
                        }
                        textViewCharacterName.text = characterInfo.name.checkIfIsEmpty()
                        textViewCharacterName.isSelected = true
                        textViewBirthYearValue.text = characterInfo.birthYear.checkIfIsEmpty()
                        textViewHeightValue.text = characterInfo.height.checkIfIsEmpty()
                        textViewHairColorValue.text = characterInfo.hairColor.checkIfIsEmpty()
                        textViewHomeWorldValue.text = characterInfo.homeworld.checkIfIsEmpty()
                        textViewGenderValue.text = characterInfo.gender.checkIfIsEmpty()
                        textViewMassValue.text = characterInfo.mass.checkIfIsEmpty()
                        textViewSkinColorValue.text = characterInfo.skinColor.checkIfIsEmpty()
                        textViewSpecieValue.text = characterInfo.species.checkIfIsEmpty()

                        if(characterInfo.homeworld.isNotEmpty()) {
                            imageViewSeePlanet.visibility = View.VISIBLE

                            imageViewSeePlanet.setOnClickListener {
                                if (characterInfo.homeworld.isNotEmpty()) {
                                    val action = CharacterInfoFragmentDirections.actionCharacterInfoFragmentToPlanetInfoFragment(characterInfo.homeworld)
                                    findNavController().navigate(action)
                                }
                            }
                        }

                        if(characterInfo.species.isNotEmpty()) {
                            imageViewSeeSpecie.visibility = View.VISIBLE

                            imageViewSeeSpecie.setOnClickListener {
                                if(characterInfo.species.isNotEmpty()) {
                                    val action = CharacterInfoFragmentDirections.actionCharacterInfoFragmentToSpecieInfoFragment(characterInfo.species)
                                    findNavController().navigate(action)
                                }
                            }
                        }

                        imageViewCharacterInfoBack.setOnClickListener {
                            findNavController().popBackStack()
                        }

                        if (characterInfo.related_vehicles.isNotEmpty()) setUpRecyclerViewVehicles(characterInfo.related_vehicles)
                        if (characterInfo.related_starships.isNotEmpty()) setUpRecyclerViewStarships(characterInfo.related_starships)
                        if (characterInfo.related_films.isNotEmpty()) setUpRecyclerViewFilms(characterInfo.related_films)
                    }
                }
            )
        }
    }

    private fun setViewVisible() {
        with(binding) {

            textViewLoadingCharacterInfo.visibility = View.GONE

            imageViewCharacter.visibility = View.VISIBLE

            textViewCharacterName.visibility = View.VISIBLE
            textViewBirthYearLabel.visibility = View.VISIBLE
            textViewHeightLabel.visibility = View.VISIBLE
            textViewHairColorLabel.visibility = View.VISIBLE
            textViewHomeWorldLabel.visibility = View.VISIBLE
            textViewGenderLabel.visibility = View.VISIBLE
            textViewMassLabel.visibility = View.VISIBLE
            textViewSkinColorLabel.visibility = View.VISIBLE
            view.visibility = View.VISIBLE
            imageViewExpandFilms.visibility = View.VISIBLE
            textViewFilms.visibility = View.VISIBLE
            view2.visibility = View.VISIBLE
            textViewPilots.visibility = View.VISIBLE
            textViewPilots.visibility = View.VISIBLE
            imageViewExpandVehicles.visibility = View.VISIBLE
            view3.visibility = View.VISIBLE
            viewFinalSeparator.visibility = View.VISIBLE
            textViewStarships.visibility = View.VISIBLE
            imageViewExpandStarships.visibility = View.VISIBLE

            imageViewCharacterInfoBack.visibility = View.VISIBLE
            textViewSpecieLabel.visibility = View.VISIBLE
        }
    }

    private fun setUpRecyclerViewVehicles(vehiclesList: ArrayList<VehicleDetail>) {

        with(binding) {
            recyclerViewVehicles.adapter = HorizontalAdapter(
                requireActivity(),
                vehiclesList,
                onVehicleClicked
            )
            recyclerViewVehicles.apply {
                layoutManager = LinearLayoutManager(
                    requireActivity(),
                    LinearLayoutManager.HORIZONTAL,
                    false
                )
            }
            recyclerViewVehicles.visibility = View.VISIBLE

            imageViewExpandVehicles.setOnClickListener {
                if (recyclerViewVehicles.visibility == View.GONE) {
                    recyclerViewVehicles.visibility = View.VISIBLE
                } else {
                    recyclerViewVehicles.visibility = View.GONE
                }
            }
        }
    }

    private fun setUpRecyclerViewFilms(filmsList: ArrayList<FilmDetail>) {
        with(binding) {
            recyclerViewFilms.adapter = HorizontalAdapter(
                requireActivity(),
                filmsList,
                onFilmClicked
            )
            recyclerViewFilms.apply {
                layoutManager = LinearLayoutManager(
                    requireActivity(),
                    LinearLayoutManager.HORIZONTAL,
                    false
                )
            }
            recyclerViewFilms.visibility = View.VISIBLE

            imageViewExpandFilms.setOnClickListener {
                if (recyclerViewFilms.visibility == View.GONE) {
                    recyclerViewFilms.visibility = View.VISIBLE
                } else {
                    recyclerViewFilms.visibility = View.GONE
                }
            }
        }
    }

    private fun setUpRecyclerViewStarships(starshipsList: ArrayList<StarshipDetail>) {
        with(binding) {
            recyclerViewStarships.adapter = HorizontalAdapter(
                requireActivity(),
                starshipsList,
                onStarshipClicked
            )
            recyclerViewStarships.apply {
                layoutManager = LinearLayoutManager(
                    requireActivity(),
                    LinearLayoutManager.HORIZONTAL,
                    false
                )
            }
            recyclerViewStarships.visibility = View.VISIBLE

            imageViewExpandStarships.setOnClickListener {
                if (recyclerViewStarships.visibility == View.GONE) {
                    recyclerViewStarships.visibility = View.VISIBLE
                } else {
                    recyclerViewStarships.visibility = View.GONE
                }
            }
        }
    }

    private val onFilmClicked: (film: FilmDetail) -> Unit = { filmDetail ->
        val action = CharacterInfoFragmentDirections.actionCharacterInfoFragmentToFilmInfoFragment(filmDetail.name)
        findNavController().navigate(action)
    }

    private val onVehicleClicked: (vehicle: VehicleDetail) -> Unit = { vehicleDetail ->
        val action = CharacterInfoFragmentDirections.actionCharacterInfoFragmentToVehicleInfoFragment(vehicleDetail.name)
        findNavController().navigate(action)
    }

    private val onStarshipClicked: (starship: StarshipDetail) -> Unit = { starshipDetail ->
        val action = CharacterInfoFragmentDirections.actionCharacterInfoFragmentToStarshipInfoFragment(starshipDetail.name)
        findNavController().navigate(action)
    }
}
