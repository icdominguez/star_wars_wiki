package com.icdominguez.starwarsencyclopedia.data.network

import com.icdominguez.starwarsencyclopedia.domain.model.*
import com.icdominguez.starwarsencyclopedia.domain.model.detail.*
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("characters")
    suspend fun getAllCharacters(): Response<List<Item>>

    @GET("characters/{name}")
    suspend fun getCharacterByName(@Path("name") name: String): Response<CharacterDetail>

    @GET("films")
    suspend fun getAllFilms(): Response<ArrayList<Item>>

    @GET("films/{name}")
    suspend fun getFilmByName(@Path("name") name: String): Response<FilmDetail>

    @GET("vehicles")
    suspend fun getAllVehicles(): Response<ArrayList<Item>>

    @GET("vehicles/{name}")
    suspend fun getVehicleByName(@Path("name") name: String): Response<VehicleDetail>

    @GET("planets")
    suspend fun getAllPlanets(): Response<ArrayList<Item>>

    @GET("planets/{name}")
    suspend fun getPlanetByName(@Path("name") name: String): Response<PlanetDetail>

    @GET("starships")
    suspend fun getAllStarships(): Response<ArrayList<Item>>

    @GET("starships/{name}")
    suspend fun getStarshipByName(@Path("name") name: String): Response<StarshipDetail>

    @GET("species")
    suspend fun getAllSpecies(): Response<ArrayList<Item>>

    @GET("species/{name}")
    suspend fun getSpecieByName(@Path("name") name: String): Response<SpecieDetail>
}
