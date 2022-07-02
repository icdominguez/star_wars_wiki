package com.icdominguez.starwarsencyclopedia.domain.useCases.starships

import com.icdominguez.starwarsencyclopedia.data.common.UseCase
import com.icdominguez.starwarsencyclopedia.data.repository.StarWarsRepository
import com.icdominguez.starwarsencyclopedia.domain.model.detail.StarshipDetail
import javax.inject.Inject

class GetStarshipByNameUseCase @Inject constructor(
    private val starWarsRepository: StarWarsRepository
) : UseCase<String, StarshipDetail>() {
    override suspend fun execute(parameters: String): StarshipDetail = starWarsRepository.getStarshipByName(parameters)
}
