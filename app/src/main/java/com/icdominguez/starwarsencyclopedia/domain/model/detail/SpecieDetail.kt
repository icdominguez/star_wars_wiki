package com.icdominguez.starwarsencyclopedia.domain.model.detail

import com.google.gson.annotations.SerializedName

data class SpecieDetail(
    val name: String = "",
    val classification: String = "",
    val designation: String = "",
    val language: String = "",
    @SerializedName("avg_lifespan")
    val avgLifespan: String = "",
    @SerializedName("avg_height")
    val avgHeight: String = "",
    @SerializedName("hair_color")
    val hairColor: ArrayList<String> = ArrayList(),
    @SerializedName("skin_color")
    val skinColor: ArrayList<String> = ArrayList(),
    @SerializedName("eye_color(s)")
    val eyeColor: ArrayList<String> = ArrayList(),
    @SerializedName("related_films")
    val relatedFilms: ArrayList<String> = ArrayList(),
    @SerializedName("related_characters")
    val relatedCharacters: ArrayList<String> = ArrayList(),
    val photo: String = ""
)
