package com.icdominguez.starwarsencyclopedia.presentation.vehicles

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
import com.icdominguez.starwarsencyclopedia.databinding.FragmentVehicleInfoBinding
import com.icdominguez.starwarsencyclopedia.domain.model.detail.CharacterDetail
import com.icdominguez.starwarsencyclopedia.domain.model.detail.FilmDetail
import com.icdominguez.starwarsencyclopedia.presentation.adapters.HorizontalAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VehicleInfoFragment : DialogFragment() {

    private lateinit var binding: FragmentVehicleInfoBinding
    private val vehiclesViewModel: VehiclesViewModel by viewModels()
    private val args: VehicleInfoFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.FullScreenDialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentVehicleInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpView()
    }

    private fun setUpView() {

        args.vehicle?.let {
            vehiclesViewModel.getVehicleByName(it)

            vehiclesViewModel.getVehicleStatus.observe(
                requireActivity(),
                { vehicleInfo ->

                    with(binding) {

                        Glide.with(requireActivity()).load(vehicleInfo.photo).into(binding.imageViewVehicle)

                        textViewVehicleName.text = vehicleInfo.name.checkIfIsEmpty()
                        textViewVehicleName.isSelected = true
                        textViewModelValue.text = vehicleInfo.model.checkIfIsEmpty()
                        textViewModelValue.isSelected = true
                        textViewManufacturerValue.text = vehicleInfo.manufacturer.checkIfIsEmpty()
                        textViewManufacturerValue.isSelected = true
                        textViewCostInCreditsValue.text = vehicleInfo.costInCredits.checkIfIsEmpty()
                        textViewCostInCreditsValue.isSelected = true
                        textViewLengthValue.text = vehicleInfo.length
                        textViewLengthValue.isSelected = true
                        textViewMaxSpeedValue.text = vehicleInfo.maxAtmospheringSpeed.checkIfIsEmpty()
                        textViewMaxSpeedValue.isSelected = true
                        textViewCrewValue.text = vehicleInfo.crew.checkIfIsEmpty()
                        textViewCrewValue.isSelected = true
                        textViewPassengersValue.text = vehicleInfo.passengers.checkIfIsEmpty()
                        textViewPassengersValue.isSelected = true
                        textViewCargoCapacitiesValue.text = vehicleInfo.cargoCapacity.checkIfIsEmpty()
                        textViewCargoCapacitiesValue.isSelected = true
                        textViewConsumablesValue.text = vehicleInfo.consumables.checkIfIsEmpty()
                        textViewConsumablesValue.isSelected = true
                        textViewClassValue.text = vehicleInfo.vehicleClass.checkIfIsEmpty()
                        textViewClassValue.isSelected = true
                    }

                    setUpRecyclerViewFilms(vehicleInfo.relatedFilms)
                    setUpRecyclerViewPilots(vehicleInfo.relatedPilots)
                    showView()
                    binding.progressBarVehicles.visibility = View.GONE

                }
            )
        }
    }

    private fun setUpRecyclerViewPilots(arrayPilots: ArrayList<CharacterDetail>) {
        if (arrayPilots.isNotEmpty()) {
            with(binding) {
                recyclerViewPilots.adapter = HorizontalAdapter(requireActivity(), arrayPilots, onPilotClicked)
                apply {
                    recyclerViewPilots.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
                }
                recyclerViewPilots.visibility = View.VISIBLE

                imageViewExpandVehicles.setOnClickListener {
                    recyclerViewPilots.visibility = if (recyclerViewPilots.visibility == View.GONE) {
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
                recyclerViewFilms.adapter = HorizontalAdapter(requireActivity(), arrayFilms, onFilmClicked)
                apply {
                    recyclerViewFilms.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
                }
                recyclerViewFilms.visibility = View.VISIBLE

                imageViewExpandFilms.setOnClickListener {
                    recyclerViewFilms.visibility = if (recyclerViewFilms.visibility == View.GONE) {
                        View.VISIBLE
                    } else {
                        View.GONE
                    }
                }

                imageViewVehicleInfoBack.setOnClickListener {
                    findNavController().popBackStack()
                }
            }
        }
    }

    private fun showView() {
        with(binding) {
            imageViewVehicle.visibility = View.VISIBLE
            textViewVehicleName.visibility = View.VISIBLE

            textViewFilms.visibility = View.VISIBLE
            textViewPilots.visibility = View.VISIBLE

            imageViewExpandVehicles.visibility = View.VISIBLE
            imageViewExpandFilms.visibility = View.VISIBLE

            viewFilmsSeparator.visibility = View.VISIBLE
            viewFinalSeparator.visibility = View.VISIBLE
            viewPilotsSeparator.visibility = View.VISIBLE

            textViewCargoCapacitiesLabel.visibility = View.VISIBLE
            textViewClassLabel.visibility = View.VISIBLE
            textViewConsumablesLabel.visibility = View.VISIBLE
            textViewCostInCreditsLabel.visibility = View.VISIBLE
            textViewCrewLabel.visibility = View.VISIBLE
            textViewLengthLabel.visibility = View.VISIBLE
            textViewManufacturerLabel.visibility = View.VISIBLE
            textViewMaxSpeedLabel.visibility = View.VISIBLE
            textViewModelLabel.visibility = View.VISIBLE
            textViewPassengersLabel.visibility = View.VISIBLE

            imageViewVehicleInfoBack.visibility = View.VISIBLE
        }
    }

    private val onFilmClicked: (film: FilmDetail) -> Unit = { film ->
        val action = VehicleInfoFragmentDirections.actionVehicleInfoFragmentToFilmInfoFragment(film.name)
        findNavController().navigate(action)
    }

    private val onPilotClicked: (character: CharacterDetail) -> Unit = { characterDetail ->
        val action = VehicleInfoFragmentDirections.actionVehicleInfoFragmentToCharacterInfoFragment(characterDetail.name)
        findNavController().navigate(action)
    }
}
