package com.icdominguez.starwarsencyclopedia.data.common

sealed class Outcome<out T> {

    data class Success<out T>(val data: T) : Outcome<T>()

    data class Failure(
        val exception: Exception,
        val message: String? = exception.message
    ) : Outcome<Nothing>()

    data class Loading(val message: String) : Outcome<Nothing>()

    override fun toString() = when (this) {
        is Success<*> -> "Success[data=$data]"
        is Failure -> "Failure[message=$message, exception=$exception]"
        is Loading -> "Loading[message=$message]"
    }
}

/**
 * `true` if [Outcome] is of type [Success] & holds non-null [Success.data].
 */
val Outcome<*>.succeeded
    get() = this is Outcome.Success && data != null

fun <T> Outcome<T>.successOr(fallback: T): T {
    return (this as? Outcome.Success<T>)?.data ?: fallback
}

val <T> Outcome<T>.data: T?
    get() = (this as? Outcome.Success)?.data