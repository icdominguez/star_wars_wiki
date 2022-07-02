package com.icdominguez.starwarsencyclopedia.domain.model.info

import com.icdominguez.starwarsencyclopedia.domain.model.detail.CharacterDetail
import com.icdominguez.starwarsencyclopedia.domain.model.detail.FilmDetail

data class VehicleInfo(
    var name: String = "",
    var model: String = "",
    var manufacturer: String = "",
    var costInCredits: String = "",
    var length: String = "",
    var maxAtmospheringSpeed: String = "",
    var crew: String = "",
    var passengers: String = "",
    var cargoCapacity: String = "",
    var consumables: String = "",
    var vehicleClass: String = "",
    var relatedPilots: ArrayList<CharacterDetail> = ArrayList(),
    var relatedFilms: ArrayList<FilmDetail> = ArrayList(),
    var photo: String = ""
)