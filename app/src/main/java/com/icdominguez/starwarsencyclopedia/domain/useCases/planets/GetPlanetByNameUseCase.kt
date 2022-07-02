package com.icdominguez.starwarsencyclopedia.domain.useCases.planets

import com.icdominguez.starwarsencyclopedia.data.common.UseCase
import com.icdominguez.starwarsencyclopedia.data.repository.StarWarsRepository
import com.icdominguez.starwarsencyclopedia.domain.model.detail.PlanetDetail
import javax.inject.Inject

class GetPlanetByNameUseCase @Inject constructor(
    private val starWarsRepository: StarWarsRepository
): UseCase<String, PlanetDetail>() {
    override suspend fun execute(parameters: String): PlanetDetail = starWarsRepository.getPlanetByName(parameters)
}