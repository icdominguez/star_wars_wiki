package com.icdominguez.starwarsencyclopedia.domain.useCases.films

import com.icdominguez.starwarsencyclopedia.data.common.UseCase
import com.icdominguez.starwarsencyclopedia.data.repository.StarWarsRepository
import com.icdominguez.starwarsencyclopedia.domain.model.detail.FilmDetail
import javax.inject.Inject

class GetFilmByNameUseCase @Inject constructor(
    private val starWarsRepository: StarWarsRepository
) : UseCase<String, FilmDetail>() {

    override suspend fun execute(parameters: String): FilmDetail = starWarsRepository.getFilmByName(parameters)
}