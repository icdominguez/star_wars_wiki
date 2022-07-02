package com.icdominguez.starwarsencyclopedia.domain.model.detail

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class CharacterDetail(
    var name: String = "",
    @SerializedName("birth_year")
    var birthYear: String = "",
    var height: String = "",
    var species: String = "",
    var mass: String = "",
    var gender: String = "",
    @SerializedName("hair_color")
    var hairColor: String = "skin_color",
    @SerializedName("skinColor")
    var skinColor: String = "",
    var homeworld: String = "",
    var photo: String = "",
    @SerializedName("related_films")
    var related_films: @RawValue ArrayList<String> = ArrayList(),
    @SerializedName("related_vehicles")
    var related_vehicles: @RawValue ArrayList<String> = ArrayList(),
    @SerializedName("related_starships")
    var related_starships: @RawValue ArrayList<String> = ArrayList()
) : Parcelable
