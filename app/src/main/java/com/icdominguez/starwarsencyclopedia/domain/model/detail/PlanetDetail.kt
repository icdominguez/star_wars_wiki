package com.icdominguez.starwarsencyclopedia.domain.model.detail

import com.google.gson.annotations.SerializedName

data class PlanetDetail(
    val name: String = "",
    @SerializedName("rotation_period")
    val rotationPeriod: String = "",
    @SerializedName("orbital_period")
    val orbitalPeriod: String = "",
    val diameter: String = "",
    val climate: String = "",
    val gravity: String = "",
    val terrain: String = "",
    @SerializedName("surface_water")
    val surfaceWater: String = "",
    val population: String = "",
    val residents: ArrayList<String> = ArrayList(),
    @SerializedName("related_films")
    val films: ArrayList<String> = ArrayList(),
    val photo: String = ""
)
