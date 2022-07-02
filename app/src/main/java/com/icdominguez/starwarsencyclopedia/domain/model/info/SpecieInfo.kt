package com.icdominguez.starwarsencyclopedia.domain.model.info

import com.google.gson.annotations.SerializedName
import com.icdominguez.starwarsencyclopedia.domain.model.detail.CharacterDetail
import com.icdominguez.starwarsencyclopedia.domain.model.detail.FilmDetail

data class SpecieInfo(
    var name: String = "",
    var classification: String = "",
    var designation: String = "",
    var language: String = "",
    var avgLifespan: String = "",
    var avgHeight: String = "",
    var hairColor: ArrayList<String> = ArrayList(),
    var skinColor: ArrayList<String> = ArrayList(),
    var eyeColor: ArrayList<String> = ArrayList(),
    var relatedFilms: ArrayList<FilmDetail> = ArrayList(),
    var relatedCharacters: ArrayList<CharacterDetail> = ArrayList(),
    var photo: String = ""
)
