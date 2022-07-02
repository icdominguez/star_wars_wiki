package com.icdominguez.starwarsencyclopedia.domain.model.info

import com.icdominguez.starwarsencyclopedia.domain.model.detail.FilmDetail
import com.icdominguez.starwarsencyclopedia.domain.model.detail.StarshipDetail
import com.icdominguez.starwarsencyclopedia.domain.model.detail.VehicleDetail

data class CharacterInfo(
    var name: String = "",
    var birthYear: String = "",
    var species: String = "",
    var height: String = "",
    var mass: String = "",
    var gender: String = "",
    var hairColor: String = "skin_color",
    var skinColor: String = "",
    var homeworld: String = "",
    var photo: String = "",
    var related_films: ArrayList<FilmDetail> = ArrayList(),
    var related_vehicles: ArrayList<VehicleDetail> = ArrayList(),
    var related_starships: ArrayList<StarshipDetail> = ArrayList()
)
