package com.icdominguez.starwarsencyclopedia.data.common

object Constants {
    val BASE_URL = "https://icdominguez-star-wars.herokuapp.com/api/" // "http://10.0.2.2:3000/api/"
    val LOG_TAG = "STAR_WARS_LOG"
}

fun String.checkIfIsEmpty(): String {
    return if (this.isNotEmpty()) this.lowercase() else "unknown"
}
