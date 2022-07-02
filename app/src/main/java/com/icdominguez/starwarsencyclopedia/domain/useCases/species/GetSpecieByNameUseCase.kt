package com.icdominguez.starwarsencyclopedia.domain.useCases.species

import com.icdominguez.starwarsencyclopedia.data.common.UseCase
import com.icdominguez.starwarsencyclopedia.data.repository.StarWarsRepository
import com.icdominguez.starwarsencyclopedia.domain.model.detail.SpecieDetail
import javax.inject.Inject

class GetSpecieByNameUseCase @Inject constructor(
    private val starWarsRepository: StarWarsRepository
): UseCase<String, SpecieDetail>() {
    override suspend fun execute(parameters: String): SpecieDetail = starWarsRepository.getSpecieByName(parameters)
}