package com.icdominguez.starwarsencyclopedia.presentation.starships

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.icdominguez.starwarsencyclopedia.data.common.Outcome
import com.icdominguez.starwarsencyclopedia.domain.model.Item
import com.icdominguez.starwarsencyclopedia.domain.model.info.StarshipInfo
import com.icdominguez.starwarsencyclopedia.domain.useCases.characters.GetCharacterByNameUseCase
import com.icdominguez.starwarsencyclopedia.domain.useCases.films.GetFilmByNameUseCase
import com.icdominguez.starwarsencyclopedia.domain.useCases.starships.GetAllStarshipsUseCase
import com.icdominguez.starwarsencyclopedia.domain.useCases.starships.GetStarshipByNameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StarshipsViewModel @Inject constructor(
    private val getAllStarshipsUseCase: GetAllStarshipsUseCase,
    private val getStarshipByNameUseCase: GetStarshipByNameUseCase,
    private val getCharacterByNameUseCase: GetCharacterByNameUseCase,
    private val getFilmByNameUseCase: GetFilmByNameUseCase,
) : ViewModel() {

    private val _starshipListObserver = MutableLiveData<List<Item>>()
    var starshipListObserver: LiveData<List<Item>> = _starshipListObserver

    private val _getStarshipStatus = MutableLiveData<StarshipInfo>()
    var getStarshipStatus: LiveData<StarshipInfo> = _getStarshipStatus

    val starshipInfo = StarshipInfo()

    fun getAllStarhips() {
        viewModelScope.launch {
            when (val response = getAllStarshipsUseCase(Unit)) {
                is Outcome.Success -> {
                    _starshipListObserver.postValue(response.data)
                }
            }
        }
    }

    fun getStarshipByName(starship: String) {
        viewModelScope.launch {
            when (val response = getStarshipByNameUseCase(starship)) {
                is Outcome.Success -> {
                    with(starshipInfo) {
                        name = response.data.name
                        model = response.data.model
                        manufacturer = response.data.manufacturer
                        costInCredits = response.data.costInCredits
                        length = response.data.length
                        maxAtmospheringSpeed = response.data.maxAtmospheringSpeed
                        crew = response.data.crew
                        passengers = response.data.passengers
                        cargo_capacity = response.data.cargo_capacity
                        consumables = response.data.consumables
                        hyperdriveRating = response.data.hyperdriveRating
                        starshipClass = response.data.starshipClass
                        photo = response.data.photo
                    }

                    val arrayJobs = ArrayList<Job>()

                    response.data.pilots.forEach { character ->
                        arrayJobs.add(viewModelScope.launch { getCharacterByName(character) })
                    }

                    response.data.films.forEach { film ->
                        arrayJobs.add(viewModelScope.launch { getFilmByName(film) })
                    }

                    arrayJobs.joinAll()
                    _getStarshipStatus.postValue(starshipInfo)
                }
            }
        }
    }

    suspend fun getCharacterByName(character: String) {
        when (val response = getCharacterByNameUseCase(character)) {
            is Outcome.Success -> {
                starshipInfo.pilots.add(response.data)
            }
        }
    }

    suspend fun getFilmByName(film: String) {
        when (val response = getFilmByNameUseCase(film)) {
            is Outcome.Success -> {
                starshipInfo.films.add(response.data)
            }
        }
    }
}
