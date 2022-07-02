package com.icdominguez.starwarsencyclopedia.data.network

import com.icdominguez.starwarsencyclopedia.domain.model.*
import com.icdominguez.starwarsencyclopedia.domain.model.detail.*
import kotlinx.coroutines.*
import javax.inject.Inject

class ApiClient @Inject constructor(private val api: ApiService) {

    suspend fun getCharacters(): List<Item> {
        return withContext(Dispatchers.IO) {
            val response = api.getAllCharacters()
            (response.body() ?: emptyList())
        }
    }

    suspend fun getFilms(): ArrayList<Item> {
        return withContext(Dispatchers.IO) {
            val response = api.getAllFilms()
            (response.body() ?: ArrayList())
        }
    }

    suspend fun getPlanets(): ArrayList<Item> {
        return withContext(Dispatchers.IO) {
            val response = api.getAllPlanets()
            (response.body() ?: ArrayList())
        }
    }

    suspend fun getVehicles(): ArrayList<Item> {
        return withContext(Dispatchers.IO) {
            val response = api.getAllVehicles()
            (response.body() ?: ArrayList())
        }
    }

    suspend fun getStarships(): ArrayList<Item> {
        return withContext(Dispatchers.IO) {
            val response = api.getAllStarships()
            (response.body() ?: ArrayList())
        }
    }

    suspend fun getSpecies(): ArrayList<Item> {
        return withContext(Dispatchers.IO) {
            val response = api.getAllSpecies()
            (response.body() ?: ArrayList())
        }
    }

    suspend fun getSpecieByName(name: String): SpecieDetail {
        return withContext(Dispatchers.IO) {
            val response = api.getSpecieByName(name)
            response.body() ?: SpecieDetail()
        }
    }

    suspend fun getCharacterByName(name: String): CharacterDetail {
        return withContext(Dispatchers.IO) {
            val response = api.getCharacterByName(name)
            response.body() ?: CharacterDetail()
        }
    }

    suspend fun getFilmByName(name: String): FilmDetail {
        return withContext(Dispatchers.IO) {
            val response = api.getFilmByName(name)
            response.body() ?: FilmDetail()
        }
    }

    suspend fun getStarshipByName(name: String): StarshipDetail {
        return withContext(Dispatchers.IO) {
            val response = api.getStarshipByName(name)
            response.body() ?: StarshipDetail()
        }
    }

    suspend fun getPlanetByName(name: String): PlanetDetail {
        return withContext(Dispatchers.IO) {
            val response = api.getPlanetByName(name)
            response.body() ?: PlanetDetail()
        }
    }

    suspend fun getVehicleByName(name: String): VehicleDetail {
        return withContext(Dispatchers.IO) {
            val response = api.getVehicleByName(name)
            response.body() ?: VehicleDetail()
        }
    }
}
