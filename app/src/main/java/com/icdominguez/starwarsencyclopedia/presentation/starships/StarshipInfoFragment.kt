package com.icdominguez.starwarsencyclopedia.presentation.starships

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
import com.icdominguez.starwarsencyclopedia.databinding.FragmentStarshipInfoBinding
import com.icdominguez.starwarsencyclopedia.domain.model.detail.CharacterDetail
import com.icdominguez.starwarsencyclopedia.domain.model.detail.FilmDetail
import com.icdominguez.starwarsencyclopedia.presentation.adapters.HorizontalAdapter
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class StarshipInfoFragment : DialogFragment() {

    private lateinit var binding: FragmentStarshipInfoBinding
    private val starshipsViewModel: StarshipsViewModel by viewModels()
    private val args: StarshipInfoFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.FullScreenDialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentStarshipInfoBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpView()
    }

    private fun setUpView() {
        args.starship?.let { starship ->
            starshipsViewModel.getStarshipByName(starship)

            starshipsViewModel.getStarshipStatus.observe(
                viewLifecycleOwner,
                { starshipInfo ->
                    with(binding) {
                        if (starshipInfo.photo.isNotEmpty()) {
                            Glide.with(requireActivity()).load(starshipInfo.photo)
                                .into(imageViewStarship)
                        } else {
                            Glide.with(requireActivity()).load(R.drawable.no_picture)
                                .into(imageViewStarship)
                        }
                        textViewStarship.text = starshipInfo.name.checkIfIsEmpty()
                        textViewModelValue.text = starshipInfo.model.checkIfIsEmpty()
                        textViewManufacturerValue.text = starshipInfo.manufacturer.checkIfIsEmpty()
                        textViewCostInCreditsValue.text =
                            starshipInfo.costInCredits.checkIfIsEmpty()
                        textViewLengthValue.text = starshipInfo.length.checkIfIsEmpty()
                        textViewMaxSpeedValue.text =
                            starshipInfo.maxAtmospheringSpeed.checkIfIsEmpty()
                        textViewCrewValue.text = starshipInfo.crew.checkIfIsEmpty()
                        textViewPassengersValue.text = starshipInfo.passengers.checkIfIsEmpty()
                        textViewCargoCapacityLabel.text =
                            starshipInfo.cargo_capacity.checkIfIsEmpty()
                        textViewConsumablesValue.text = starshipInfo.consumables.checkIfIsEmpty()
                        textViewHyperdriveRatingValue.text = starshipInfo.model.checkIfIsEmpty()
                        textViewMGLTValue.text = starshipInfo.mglt.checkIfIsEmpty()
                        textViewClassValue.text = starshipInfo.starshipClass.checkIfIsEmpty()

                        showView()

                        setUpRecyclerViewFilms(starshipInfo.films)
                        setUpRecyclerViewPilots(starshipInfo.pilots)

                        imageViewStarshipInfoBack.setOnClickListener {
                            findNavController().popBackStack()
                        }
                    }
                }
            )
        }
    }

    private fun setUpRecyclerViewPilots(arrayPilots: ArrayList<CharacterDetail>) {
        if (arrayPilots.isNotEmpty()) {
            with(binding) {
                recyclerViewPilots.adapter =
                    HorizontalAdapter(requireActivity(), arrayPilots, onPilotClicked)
                apply {
                    recyclerViewPilots.layoutManager = LinearLayoutManager(requireActivity(),
                        LinearLayoutManager.HORIZONTAL,
                        false)
                }
                recyclerViewPilots.visibility = View.VISIBLE

                imageViewExpandPilots.setOnClickListener {
                    recyclerViewPilots.visibility =
                        if (recyclerViewPilots.visibility == View.GONE) {
                            View.VISIBLE
                        } else {
                            View.GONE
                        }
                }
            }
        }
    }

    private fun setUpRecyclerViewFilms(arrayFilms: ArrayList<FilmDetail>) {
        if (arrayFilms.isNotEmpty()) {
            with(binding) {
                recyclerViewFilms.adapter =
                    HorizontalAdapter(requireActivity(), arrayFilms, onFilmClicked)
                apply {
                    recyclerViewFilms.layoutManager = LinearLayoutManager(requireActivity(),
                        LinearLayoutManager.HORIZONTAL,
                        false)
                }
                recyclerViewFilms.visibility = View.VISIBLE

                imageViewExpandFilms.setOnClickListener {
                    recyclerViewFilms.visibility = if (recyclerViewFilms.visibility == View.GONE) {
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

            textViewStarship.visibility = View.VISIBLE
            imageViewStarship.visibility = View.VISIBLE

            imageViewExpandPilots.visibility = View.VISIBLE
            imageViewExpandFilms.visibility = View.VISIBLE

            textViewFilms.visibility = View.VISIBLE
            textViewPilots.visibility = View.VISIBLE

            viewFilmsSeparator.visibility = View.VISIBLE
            viewFinalSeparator.visibility = View.VISIBLE
            viewPilotsSeparator.visibility = View.VISIBLE

            textViewClassLabel.visibility = View.VISIBLE
            textViewCargoCapacityLabel.visibility = View.VISIBLE
            textViewMaxSpeedLabel.visibility = View.VISIBLE
            textViewModelLabel.visibility = View.VISIBLE
            textViewPassengersLabel.visibility = View.VISIBLE
            textViewMGLTLabel.visibility = View.VISIBLE
            textViewLengthLabel.visibility = View.VISIBLE
            textViewHyperdrivingRatingLabel.visibility = View.VISIBLE
            textViewCrewLabel.visibility = View.VISIBLE
            textViewCostInCreditsLabel.visibility = View.VISIBLE
            textViewConsumableLabel.visibility = View.VISIBLE
            textViewManufacturerLabel.visibility = View.VISIBLE

            imageViewStarshipInfoBack.visibility = View.VISIBLE
        }
    }

    private val onFilmClicked: (film: FilmDetail) -> Unit = { film ->
        val action =
            StarshipInfoFragmentDirections.actionStarshipInfoFragmentToFilmInfoFragment(film.name)
        findNavController().navigate(action)
    }

    private val onPilotClicked: (character: CharacterDetail) -> Unit = { characterDetail ->
        val action =
            StarshipInfoFragmentDirections.actionStarshipInfoFragmentToCharacterInfoFragment(
                characterDetail.name)
        findNavController().navigate(action)
    }
}
