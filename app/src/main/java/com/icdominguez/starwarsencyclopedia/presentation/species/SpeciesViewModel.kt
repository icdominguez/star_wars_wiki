package com.icdominguez.starwarsencyclopedia.presentation.species

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.icdominguez.starwarsencyclopedia.data.common.Outcome
import com.icdominguez.starwarsencyclopedia.domain.model.Item
import com.icdominguez.starwarsencyclopedia.domain.model.info.SpecieInfo
import com.icdominguez.starwarsencyclopedia.domain.useCases.characters.GetCharacterByNameUseCase
import com.icdominguez.starwarsencyclopedia.domain.useCases.films.GetFilmByNameUseCase
import com.icdominguez.starwarsencyclopedia.domain.useCases.species.GetAllSpeciesUseCase
import com.icdominguez.starwarsencyclopedia.domain.useCases.species.GetSpecieByNameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SpeciesViewModel @Inject constructor(
    private val getAllSpeciesUseCase: GetAllSpeciesUseCase,
    private val getSpecieByNameUseCase: GetSpecieByNameUseCase,
    private val getCharacterByNameUseCase: GetCharacterByNameUseCase,
    private val getFilmByNameUseCase: GetFilmByNameUseCase
) : ViewModel() {

    private val _speciesListObserver = MutableLiveData<List<Item>>()
    var speciesListObserver: LiveData<List<Item>> = _speciesListObserver

    private val _specieObserver = MutableLiveData<SpecieInfo>()
    var specieObserver : LiveData<SpecieInfo> = _specieObserver

    val specieInfo = SpecieInfo()

    fun getAllSpecies() {
        viewModelScope.launch {
            when(val response = getAllSpeciesUseCase(Unit)) {
                is Outcome.Success -> _speciesListObserver.postValue(response.data)
            }
        }
    }

    fun getSpecieByName(specieName: String) {
        viewModelScope.launch {
            when(val response = getSpecieByNameUseCase(specieName)) {
                is Outcome.Success -> {
                    with(specieInfo) {
                        name = response.data.name
                        classification = response.data.classification
                        designation = response.data.designation
                        language = response.data.language
                        avgLifespan = response.data.avgLifespan
                        avgHeight = response.data.avgHeight
                        hairColor = response.data.hairColor
                        skinColor = response.data.skinColor
                        eyeColor = response.data.eyeColor
                        photo = response.data.photo

                        val arrayJobs = ArrayList<Job>()

                        response.data.relatedFilms.forEach { filmName ->
                            arrayJobs.add(viewModelScope.launch { getFilmByName(filmName) })
                        }

                        response.data.relatedCharacters.forEach { characterName ->
                            arrayJobs.add(viewModelScope.launch { getCharacterByName(characterName) })
                        }

                        arrayJobs.joinAll()

                        _specieObserver.postValue(specieInfo)

                    }
                }
            }
        }
    }

    suspend fun getFilmByName(filmName: String) {
        when(val response = getFilmByNameUseCase(filmName)) {
            is Outcome.Success -> {
                specieInfo.relatedFilms.add(response.data)
            }
        }
    }

    suspend fun getCharacterByName(characterName: String) {
        when(val response = getCharacterByNameUseCase(characterName)) {
            is Outcome.Success -> {
                specieInfo.relatedCharacters.add(response.data)
            }
        }
    }
}