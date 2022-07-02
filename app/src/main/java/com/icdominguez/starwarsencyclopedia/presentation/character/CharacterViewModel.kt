package com.icdominguez.starwarsencyclopedia.presentation.character

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.icdominguez.starwarsencyclopedia.data.common.Outcome
import com.icdominguez.starwarsencyclopedia.data.common.Constants.LOG_TAG
import com.icdominguez.starwarsencyclopedia.domain.model.info.CharacterInfo
import com.icdominguez.starwarsencyclopedia.domain.model.Item
import com.icdominguez.starwarsencyclopedia.domain.useCases.characters.GetAllCharactersUseCase
import com.icdominguez.starwarsencyclopedia.domain.useCases.characters.GetCharacterByNameUseCase
import com.icdominguez.starwarsencyclopedia.domain.useCases.films.GetFilmByNameUseCase
import com.icdominguez.starwarsencyclopedia.domain.useCases.starships.GetStarshipByNameUseCase
import com.icdominguez.starwarsencyclopedia.domain.useCases.vehicles.GetVehicleByNameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterViewModel @Inject constructor(
    private val getAllCharactersUseCase: GetAllCharactersUseCase,
    private val getCharacterByNameUseCase: GetCharacterByNameUseCase,
    private val getStarshipByNameUseCase: GetStarshipByNameUseCase,
    private val getVehicleByNameUseCase: GetVehicleByNameUseCase,
    private val getFilmByNameUseCase: GetFilmByNameUseCase
) : ViewModel() {

    private val _characterListObserver = MutableLiveData<Outcome<List<Item>>>()
    var characterDetailListObserver: LiveData<Outcome<List<Item>>> = _characterListObserver

    private val _getCharacterStatus = MutableLiveData<CharacterInfo>()
    val getCharacterStatus: LiveData<CharacterInfo> get() = _getCharacterStatus

    val characterInfo = CharacterInfo()

    fun getCharacters() {
        viewModelScope.launch {
            _characterListObserver.postValue(getAllCharactersUseCase(Unit))
        }
    }

    fun getCharacterByName(name: String) {
        viewModelScope.launch {
            val response = getCharacterByNameUseCase(name)
            when (response) {
                is Outcome.Success -> {
                    if (response.data.name.isEmpty()) {
                        Log.i(LOG_TAG, "Server respond with no data for character: $name")
                    } else {
                        Log.i(LOG_TAG, "Info get for character: $name ${response.data}")

                        characterInfo.name = response.data.name
                        characterInfo.birthYear = response.data.birthYear
                        characterInfo.height = response.data.height
                        characterInfo.mass = response.data.mass
                        characterInfo.gender = response.data.gender
                        characterInfo.hairColor = response.data.hairColor
                        characterInfo.skinColor = response.data.skinColor
                        characterInfo.homeworld = response.data.homeworld
                        characterInfo.species = response.data.species
                        characterInfo.photo = response.data.photo

                        val arrayJobs = ArrayList<Job>()

                        response.data.related_starships.forEach { starship ->
                            arrayJobs.add(viewModelScope.launch { getStarshipByName(starship) })
                        }

                        response.data.related_vehicles.forEach { vehicle ->
                            arrayJobs.add(viewModelScope.launch { getVehicleByName(vehicle) })
                        }

                        response.data.related_films.forEach { film ->
                            arrayJobs.add(viewModelScope.launch { getFilmByName(film) })
                        }

                        arrayJobs.joinAll()

                        Log.i(LOG_TAG, "And this is the final object: ${Gson().toJson(characterInfo)}")
                        _getCharacterStatus.postValue(characterInfo)

                    }
                }

                is Outcome.Failure -> {
                    Log.i(LOG_TAG,"There was an error getting info for character $name ${response.exception}")
                }
                is Outcome.Loading -> TODO()
            }
        }
    }

    suspend fun getVehicleByName(vehicle: String) {

        when (val response = getVehicleByNameUseCase(vehicle)) {
            is Outcome.Success -> {
                if (response.data.name.isEmpty()) {
                    Log.i(LOG_TAG, "No data retrieve for vehicle $vehicle")
                } else {
                    Log.i(LOG_TAG, "${response.data}")
                    characterInfo.related_vehicles.add(response.data)
                }
            }
            is Outcome.Failure -> {
                Log.i(
                    LOG_TAG,
                    "There was an error getting vehicle $vehicle with the exception: ${response
                        .exception}"
                )
            }
        }
    }

    suspend fun getStarshipByName(starship: String) {
        when (val response = getStarshipByNameUseCase(starship)) {
            is Outcome.Success -> {
                if (response.data.name.isEmpty()) {
                    Log.i(LOG_TAG, "No data retrieve for vehicle $starship")
                } else {
                    Log.i(LOG_TAG, "${response.data}")
                    characterInfo.related_starships.add(response.data)
                }
            }
            is Outcome.Failure -> {
                Log.i(
                    LOG_TAG,
                    "There was an error getting starship $starship with the exception: ${response
                        .exception}"
                )
            }
        }
    }

    suspend fun getFilmByName(film: String) {
        when (val response = getFilmByNameUseCase(film)) {
            is Outcome.Success -> {
                if (response.data.name.isEmpty()) {
                    Log.i(LOG_TAG, "No data retrieve film: $film")
                } else {
                    Log.i(LOG_TAG, "${response.data}")
                    characterInfo.related_films.add(response.data)
                }
            }
            is Outcome.Failure -> {
                Log.i(
                    LOG_TAG,
                    "There was an error getting film $film with the exception: ${response
                        .exception}"
                )
            }
        }
    }
}
