package com.icdominguez.starwarsencyclopedia.domain.model.info

import com.google.gson.annotations.SerializedName
import com.icdominguez.starwarsencyclopedia.domain.model.detail.CharacterDetail
import com.icdominguez.starwarsencyclopedia.domain.model.detail.FilmDetail

data class PlanetInfo(
    var name: String = "",
    var rotationPeriod: String = "",
    var orbitalPeriod: String = "",
    var diameter: String = "",
    var climate: String = "",
    var gravity: String = "",
    var terrain: String = "",
    var surfaceWater: String = "",
    var population: String = "",
    var residents: ArrayList<CharacterDetail> = ArrayList(),
    var films: ArrayList<FilmDetail> = ArrayList(),
    var photo: String = ""
)
