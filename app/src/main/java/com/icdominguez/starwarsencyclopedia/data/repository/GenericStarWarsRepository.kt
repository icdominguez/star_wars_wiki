package com.icdominguez.starwarsencyclopedia.data.repository

import com.icdominguez.starwarsencyclopedia.data.network.ApiClient
import com.icdominguez.starwarsencyclopedia.data.repository.StarWarsRepository
import com.icdominguez.starwarsencyclopedia.domain.model.*
import com.icdominguez.starwarsencyclopedia.domain.model.detail.*
import javax.inject.Inject

class GenericStarWarsRepository @Inject constructor(private val api: ApiClient) : StarWarsRepository {

    override suspend fun getAllCharacters(): List<Item> = api.getCharacters()
    override suspend fun getCharacterByName(name: String): CharacterDetail = api.getCharacterByName(name)

    override suspend fun getAllStarships(): List<Item> = api.getStarships()
    override suspend fun getStarshipByName(name: String): StarshipDetail = api.getStarshipByName(name)

    override suspend fun getAllVehicles(): List<Item> = api.getVehicles()
    override suspend fun getVehicleByName(name: String): VehicleDetail = api.getVehicleByName(name)

    override suspend fun getAllFilms(): List<Item> = api.getFilms()
    override suspend fun getFilmByName(name: String): FilmDetail = api.getFilmByName(name)

    override suspend fun getAllSpecies(): List<Item> = api.getSpecies()
    override suspend fun getSpecieByName(name: String): SpecieDetail = api.getSpecieByName(name)

    override suspend fun getAllPlanets(): List<Item> = api.getPlanets()
    override suspend fun getPlanetByName(name: String): PlanetDetail = api.getPlanetByName(name)
}
