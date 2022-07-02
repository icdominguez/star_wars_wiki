package com.icdominguez.starwarsencyclopedia.presentation.planets

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
import com.icdominguez.starwarsencyclopedia.databinding.FragmentPlanetInfoBinding
import com.icdominguez.starwarsencyclopedia.domain.model.detail.CharacterDetail
import com.icdominguez.starwarsencyclopedia.domain.model.detail.FilmDetail
import com.icdominguez.starwarsencyclopedia.presentation.adapters.HorizontalAdapter
import dagger.hilt.android.AndroidEntryPoint
import java.util.ArrayList

@AndroidEntryPoint
class PlanetInfoFragment : DialogFragment() {

    private lateinit var binding: FragmentPlanetInfoBinding
    private val viewModel: PlanetViewModel by viewModels()
    private val args: PlanetInfoFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.FullScreenDialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentPlanetInfoBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpView()
    }

    private fun setUpView() {

        args.planet?.let {
            viewModel.getPlanetByName(it)

            viewModel.planetObserver.observe(viewLifecycleOwner, { planetInfo ->
                with(binding) {
                    if(planetInfo.photo.isNotEmpty()) {
                        Glide.with(requireActivity()).load(planetInfo.photo).into(binding.imageViewPlanet)
                    } else {
                        Glide.with(requireActivity()).load(R.drawable.no_picture).into(binding.imageViewPlanet)
                    }

                    textViewPlanetName.text = planetInfo.name.checkIfIsEmpty()
                    textViewRotationPeriodValue.text = planetInfo.rotationPeriod.checkIfIsEmpty()
                    textViewOrbitalPeriodValue.text = planetInfo.orbitalPeriod.checkIfIsEmpty()
                    textViewDiameterValue.text = planetInfo.diameter.checkIfIsEmpty()
                    textViewClimateValue.text = planetInfo.climate.checkIfIsEmpty()
                    textViewGravityValue.text = planetInfo.gravity.checkIfIsEmpty()
                    textViewTerrainValue.text = planetInfo.terrain.checkIfIsEmpty()
                    textViewSurfaceWaterValue.text = planetInfo.surfaceWater.checkIfIsEmpty()
                    textViewPopulationValue.text = planetInfo.population.checkIfIsEmpty()

                    setUpRecyclerViewFilms(planetInfo.films)
                    setUpRecyclerViewResidents(planetInfo.residents)
                    showView()
                    progressBarPlanetInfo.visibility = View.GONE

                    imageViewSpecieInfoBack.setOnClickListener {
                        findNavController().popBackStack()
                    }

                }
            })
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

    private fun setUpRecyclerViewResidents(relatedResidents: ArrayList<CharacterDetail>) {
        if (relatedResidents.isNotEmpty()) {
            with(binding){
                recyclerViewResidents.adapter = HorizontalAdapter(requireActivity(), relatedResidents, onResidentClicked)
                apply {
                    recyclerViewResidents.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
                }
                recyclerViewResidents.visibility = View.VISIBLE

                imageViewExpandResidents.setOnClickListener {
                    recyclerViewResidents.visibility = if(recyclerViewResidents.visibility == View.GONE) {
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

            imageViewPlanet.visibility = View.VISIBLE
            textViewPlanetName.visibility = View.VISIBLE

            textViewFilms.visibility = View.VISIBLE
            textViewResidents.visibility = View.VISIBLE

            imageViewExpandFilms.visibility = View.VISIBLE
            imageViewExpandResidents.visibility = View.VISIBLE

            viewFilmsSeparator.visibility = View.VISIBLE
            viewResidentsSeparator.visibility = View.VISIBLE
            viewRecyclerViewsSeparator.visibility = View.VISIBLE

            textViewClimateLabel.visibility = View.VISIBLE
            textViewDiameterLabel.visibility = View.VISIBLE
            textViewGravityLabel.visibility = View.VISIBLE
            textViewOrbitalPeriodLabel.visibility = View.VISIBLE
            textViewPopulationLabel.visibility = View.VISIBLE
            textViewRotationPeriodLabel.visibility = View.VISIBLE
            textViewSurfaceWaterLabel.visibility = View.VISIBLE
            textViewTerrainLabel.visibility = View.VISIBLE

            imageViewSpecieInfoBack.visibility = View.VISIBLE
        }
    }

    private val onFilmClicked: (film: FilmDetail) -> Unit = { filmDetail ->
        val action = PlanetInfoFragmentDirections.actionPlanetInfoFragmentToFilmInfoFragment(filmDetail.name)
        findNavController().navigate(action)
    }

    private val onResidentClicked: (character: CharacterDetail) -> Unit = { characterDetail ->
        val action = PlanetInfoFragmentDirections.actionPlanetInfoFragmentToCharacterInfoFragment(characterDetail.name)
        findNavController().navigate(action)
    }
}
