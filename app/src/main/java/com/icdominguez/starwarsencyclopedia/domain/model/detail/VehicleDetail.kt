package com.icdominguez.starwarsencyclopedia.domain.model.detail

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class VehicleDetail(
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
    var cargoCapacity: String = "",
    var consumables: String = "",
    @SerializedName("vehicle_class")
    var vehicleClass: String = "",
    @SerializedName("related_pilots")
    var relatedPilots: ArrayList<String> = ArrayList(),
    @SerializedName("related_films")
    var relatedFilms: ArrayList<String> = ArrayList(),
    var photo: String = ""
): Parcelable
