package com.icdominguez.starwarsencyclopedia.data.repository

import com.icdominguez.starwarsencyclopedia.domain.model.*
import com.icdominguez.starwarsencyclopedia.domain.model.detail.*

interface StarWarsRepository {
    suspend fun getAllCharacters(): List<Item>
    suspend fun getCharacterByName(name: String): CharacterDetail

    suspend fun getAllStarships(): List<Item>
    suspend fun getStarshipByName(name: String): StarshipDetail

    suspend fun getAllVehicles(): List<Item>
    suspend fun getVehicleByName(name: String): VehicleDetail

    suspend fun getAllFilms(): List<Item>
    suspend fun getFilmByName(name: String): FilmDetail

    suspend fun getAllSpecies(): List<Item>
    suspend fun getSpecieByName(name: String): SpecieDetail

    suspend fun getAllPlanets(): List<Item>
    suspend fun getPlanetByName(name: String): PlanetDetail
}
