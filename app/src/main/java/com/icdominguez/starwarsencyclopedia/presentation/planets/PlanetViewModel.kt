package com.icdominguez.starwarsencyclopedia.presentation.planets

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.icdominguez.starwarsencyclopedia.data.common.Outcome
import com.icdominguez.starwarsencyclopedia.domain.model.Item
import com.icdominguez.starwarsencyclopedia.domain.model.info.PlanetInfo
import com.icdominguez.starwarsencyclopedia.domain.useCases.characters.GetCharacterByNameUseCase
import com.icdominguez.starwarsencyclopedia.domain.useCases.films.GetFilmByNameUseCase
import com.icdominguez.starwarsencyclopedia.domain.useCases.planets.GetAllPlanetsUseCase
import com.icdominguez.starwarsencyclopedia.domain.useCases.planets.GetPlanetByNameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlanetViewModel @Inject constructor(
    private val getAllPlanetsUseCase: GetAllPlanetsUseCase,
    private val getPlanetByNameUseCase: GetPlanetByNameUseCase,
    private val getFilmByNameUseCase: GetFilmByNameUseCase,
    private val getCharacterByNameUseCase: GetCharacterByNameUseCase
) : ViewModel() {

    private val _planetsListObserver = MutableLiveData<List<Item>>()
    var planetsListObserver: LiveData<List<Item>> = _planetsListObserver

    private val _planetObserver = MutableLiveData<PlanetInfo>()
    var planetObserver: LiveData<PlanetInfo> = _planetObserver

    val planetInfo = PlanetInfo()

    fun getPlanets() {
        viewModelScope.launch {
            when(val response = getAllPlanetsUseCase(Unit)) {
                is Outcome.Success -> {
                    _planetsListObserver.postValue(response.data)
                }
            }
        }
    }

    fun getPlanetByName(planetName: String) {
        viewModelScope.launch {
            when(val response = getPlanetByNameUseCase(planetName)) {
                is Outcome.Success -> {

                    with(planetInfo) {
                        name = response.data.name
                        rotationPeriod = response.data.rotationPeriod
                        orbitalPeriod = response.data.orbitalPeriod
                        diameter = response.data.diameter
                        climate = response.data.climate
                        gravity = response.data.gravity
                        terrain = response.data.terrain
                        surfaceWater = response.data.surfaceWater
                        population = response.data.population
                    }

                    val arrayJobs = ArrayList<Job>()

                    response.data.residents.forEach {
                        arrayJobs.add(viewModelScope.launch { getCharacterByName(it) })
                    }

                    response.data.films.forEach {
                        arrayJobs.add(viewModelScope.launch { getFilmByName(it) })
                    }

                    arrayJobs.joinAll()

                    _planetObserver.postValue(planetInfo)
                }
            }
        }
    }

    suspend fun getFilmByName(filmName: String) {
        when(val response = getFilmByNameUseCase(filmName)) {
            is Outcome.Success -> {
                planetInfo.films.add(response.data)
            }
        }
    }

    suspend fun getCharacterByName(characterName: String) {
        when(val response = getCharacterByNameUseCase(characterName)) {
            is Outcome.Success -> {
                planetInfo.residents.add(response.data)
            }
        }
    }
}
