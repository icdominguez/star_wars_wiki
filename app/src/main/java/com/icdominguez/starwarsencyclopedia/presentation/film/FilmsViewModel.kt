package com.icdominguez.starwarsencyclopedia.presentation.film

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.icdominguez.starwarsencyclopedia.data.common.Outcome
import com.icdominguez.starwarsencyclopedia.data.common.Constants.LOG_TAG
import com.icdominguez.starwarsencyclopedia.domain.model.detail.FilmDetail
import com.icdominguez.starwarsencyclopedia.domain.model.Item
import com.icdominguez.starwarsencyclopedia.domain.useCases.films.GetAllFilmsUseCase
import com.icdominguez.starwarsencyclopedia.domain.useCases.films.GetFilmByNameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FilmsViewModel @Inject constructor(
    private val getAllFilmsUseCase: GetAllFilmsUseCase,
    private val getFilmByNameUseCase: GetFilmByNameUseCase
) : ViewModel() {

    private val _filmListObserver = MutableLiveData<List<Item>>()
    var filmListObserver: LiveData<List<Item>> = _filmListObserver

    private val _getFilmByNameStatus = MutableLiveData<FilmDetail>()
    val getFilmByNameStatus get() = _getFilmByNameStatus

    fun getFilms() {
        viewModelScope.launch {
            when(val response = getAllFilmsUseCase(Unit)) {
                is Outcome.Success -> {
                    if(response.data.isNotEmpty()) {
                        _filmListObserver.postValue(response.data)
                    } else {
                        Log.i(LOG_TAG, "No data received from Films")
                    }
                }

                is Outcome.Failure -> {
                    Log.e(LOG_TAG, "There was an error getting the films: ${response.exception}")
                }
            }
        }
    }

    fun getFilmByName(film: String) {
        viewModelScope.launch {
            when(val response = getFilmByNameUseCase(film)) {
                is Outcome.Success -> {
                    if(response.data.name.isNotEmpty()) {
                        _getFilmByNameStatus.postValue(response.data)
                    } else {
                        Log.e(LOG_TAG, "No data received or film $film")
                    }
                }

                is Outcome.Failure -> {
                    Log.e(LOG_TAG, "There was an error getting films: $film")
                }
            }
        }
    }
}
