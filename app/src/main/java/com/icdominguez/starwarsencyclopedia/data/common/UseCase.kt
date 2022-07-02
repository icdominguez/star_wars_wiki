package com.icdominguez.starwarsencyclopedia.data.common

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

abstract class UseCase<in P, R> {
    suspend operator fun invoke(parameters: P): Outcome<R> {
        return try {
            withContext(Dispatchers.IO) {
                execute(parameters).let {
                    Outcome.Success(it)
                }
            }
        } catch (exception: Exception) {
            Outcome.Failure(exception)
        }
    }

    protected abstract suspend fun execute(parameters: P): R
}
