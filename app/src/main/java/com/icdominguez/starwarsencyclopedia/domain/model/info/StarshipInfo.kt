package com.icdominguez.starwarsencyclopedia.domain.model.info

import com.icdominguez.starwarsencyclopedia.domain.model.detail.CharacterDetail
import com.icdominguez.starwarsencyclopedia.domain.model.detail.FilmDetail

data class StarshipInfo(
    var name: String = "",
    var model: String = "",
    var manufacturer: String = "",
    var costInCredits: String = "",
    var length: String = "",
    var maxAtmospheringSpeed: String = "",
    var crew: String = "",
    var passengers: String = "",
    var cargo_capacity: String = "",
    var consumables: String = "",
    var hyperdriveRating: String = "",
    var mglt: String = "",
    var starshipClass: String = "",
    var pilots: ArrayList<CharacterDetail> = ArrayList(),
    var films: ArrayList<FilmDetail> = ArrayList(),
    var photo: String = ""
)
