package com.icdominguez.starwarsencyclopedia.presentation.vehicles

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.icdominguez.starwarsencyclopedia.data.common.Outcome
import com.icdominguez.starwarsencyclopedia.data.common.Constants.LOG_TAG
import com.icdominguez.starwarsencyclopedia.domain.model.Item
import com.icdominguez.starwarsencyclopedia.domain.model.info.VehicleInfo
import com.icdominguez.starwarsencyclopedia.domain.useCases.characters.GetCharacterByNameUseCase
import com.icdominguez.starwarsencyclopedia.domain.useCases.films.GetFilmByNameUseCase
import com.icdominguez.starwarsencyclopedia.domain.useCases.vehicles.GetAllVehiclesUseCase
import com.icdominguez.starwarsencyclopedia.domain.useCases.vehicles.GetVehicleByNameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VehiclesViewModel @Inject constructor(
    private val getAllVehiclesUseCase: GetAllVehiclesUseCase,
    private val getVehicleByNameUseCase: GetVehicleByNameUseCase,
    private val getCharacterByNameUseCase: GetCharacterByNameUseCase,
    private val getFilmByNameUseCase: GetFilmByNameUseCase
) : ViewModel() {

    private val _getAllVehiclesStatus = MutableLiveData<List<Item>>()
    val getAllVehiclesStatus: LiveData<List<Item>> get() = _getAllVehiclesStatus

    private val _getVehicleStatus = MutableLiveData<VehicleInfo>()
    val getVehicleStatus get() = _getVehicleStatus

    val vehicleInfo = VehicleInfo()

    fun getAllVehicles() {
        viewModelScope.launch {

            when(val response = getAllVehiclesUseCase(Unit)) {
                is Outcome.Success -> {
                    _getAllVehiclesStatus.postValue(response.data)
                }

                is Outcome.Failure -> {
                    Log.i(LOG_TAG,"There was an error getting vehicles: ${response.exception}")
                }
            }
        }
    }

    fun getVehicleByName(vehicle: String) {
        viewModelScope.launch {

            when(val response = getVehicleByNameUseCase(vehicle)) {
                is Outcome.Success -> {

                    var arrayJobs = ArrayList<Job>()

                    response.data.relatedFilms.forEach { film ->
                        arrayJobs.add(viewModelScope.launch { getFilmByName(film) })
                    }

                    response.data.relatedPilots.forEach { character ->
                        getCharacterByName(character)
                    }

                    arrayJobs.joinAll()

                    with(vehicleInfo) {
                        name = response.data.name
                        model = response.data.model
                        manufacturer = response.data.manufacturer
                        costInCredits = response.data.costInCredits
                        length = response.data.length
                        maxAtmospheringSpeed = response.data.maxAtmospheringSpeed
                        passengers = response.data.passengers
                        cargoCapacity = response.data.cargoCapacity
                        consumables = response.data.consumables
                        vehicleClass = response.data.vehicleClass
                        photo = response.data.photo
                    }

                    _getVehicleStatus.postValue(vehicleInfo)
                }

                is Outcome.Failure -> {
                    Log.i(LOG_TAG, "There was an error getting vehicle: $vehicle")
                }
            }
        }
    }

    private suspend fun getFilmByName(film: String) {

        when(val response = getFilmByNameUseCase(film)) {
            is Outcome.Success -> {
                vehicleInfo.relatedFilms.add(response.data)
            }

            is Outcome.Failure -> {
                Log.i(LOG_TAG, "There was an error getting vehicle: $film")
            }
        }
    }

    private suspend fun getCharacterByName(character: String) {

        when(val response = getCharacterByNameUseCase(character)) {
            is Outcome.Success -> {
                vehicleInfo.relatedPilots.add(response.data)
            }
            is Outcome.Failure -> {
                Log.i(LOG_TAG, "There was an error getting vehicle: $character")
            }
        }
    }
}
