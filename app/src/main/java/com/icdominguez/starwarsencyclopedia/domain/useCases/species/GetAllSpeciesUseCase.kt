package com.icdominguez.starwarsencyclopedia.domain.useCases.species

import com.icdominguez.starwarsencyclopedia.data.common.UseCase
import com.icdominguez.starwarsencyclopedia.data.repository.StarWarsRepository
import com.icdominguez.starwarsencyclopedia.domain.model.Item
import javax.inject.Inject

class GetAllSpeciesUseCase @Inject constructor(
    private val starWarsRepository: StarWarsRepository
) : UseCase<Unit, List<Item>>() {
    override suspend fun execute(parameters: Unit): List<Item> = starWarsRepository.getAllSpecies()
}