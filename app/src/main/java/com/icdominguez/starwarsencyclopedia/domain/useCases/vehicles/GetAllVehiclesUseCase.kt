package com.icdominguez.starwarsencyclopedia.domain.useCases.vehicles

import com.icdominguez.starwarsencyclopedia.data.common.UseCase
import com.icdominguez.starwarsencyclopedia.data.repository.StarWarsRepository
import com.icdominguez.starwarsencyclopedia.domain.model.Item
import javax.inject.Inject

class GetAllVehiclesUseCase @Inject constructor(
    private var starWarsRepository: StarWarsRepository
) : UseCase<Unit, List<Item>>() {
    override suspend fun execute(parameters: Unit): List<Item> = starWarsRepository.getAllVehicles()
}