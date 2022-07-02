package com.icdominguez.starwarsencyclopedia.domain.useCases.vehicles

import com.icdominguez.starwarsencyclopedia.data.common.UseCase
import com.icdominguez.starwarsencyclopedia.data.repository.StarWarsRepository
import com.icdominguez.starwarsencyclopedia.domain.model.detail.VehicleDetail
import javax.inject.Inject

class GetVehicleByNameUseCase @Inject constructor(
    private val starWarsRepository: StarWarsRepository
) : UseCase<String, VehicleDetail>() {
    override suspend fun execute(parameters: String): VehicleDetail = starWarsRepository.getVehicleByName(parameters)
}
