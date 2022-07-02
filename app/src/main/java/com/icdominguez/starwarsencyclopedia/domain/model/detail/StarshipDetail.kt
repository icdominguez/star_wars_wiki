package com.icdominguez.starwarsencyclopedia.domain.model.detail

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class StarshipDetail(
    var name: String = "",
    var model: String = "",
    var manufacturer: String = "",
    @SerializedName("cost_in_credits")
    var costInCredits: String = "",
    var length: String = "",
    @SerializedName("max_atmosphering_speed")
    var maxAtmospheringSpeed: String = "",
    var crew: String = "",
    var passengers: String = "",
    @SerializedName("cargo_capacity")
    var cargo_capacity: String = "",
    var consumables: String = "",
    @SerializedName("hyperdrive_rating")
    var hyperdriveRating: String = "",
    @SerializedName("MGLT")
    var mglt: String = "",
    @SerializedName("starship_class")
    var starshipClass: String = "",
    var pilots: ArrayList<String> = ArrayList(),
    var films: ArrayList<String> = ArrayList(),
    var photo: String = ""
): Parcelable
