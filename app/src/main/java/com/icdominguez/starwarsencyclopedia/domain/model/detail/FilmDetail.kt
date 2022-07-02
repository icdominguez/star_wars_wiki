package com.icdominguez.starwarsencyclopedia.domain.model.detail

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class FilmDetail(
    var name: String = "",
    @SerializedName("date_created")
    var dateCreated: String = "",
    var director: String = "",
    @SerializedName("producer(s)")
    var producers: ArrayList<String> = ArrayList(),
    @SerializedName("opening_cawl")
    var openingCawl: String = "",
    var photo: String = "",
): Parcelable
