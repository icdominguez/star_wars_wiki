package com.icdominguez.starwarsencyclopedia.domain.useCases.characters

import com.icdominguez.starwarsencyclopedia.data.common.UseCase
import com.icdominguez.starwarsencyclopedia.data.repository.StarWarsRepository
import com.icdominguez.starwarsencyclopedia.domain.model.detail.CharacterDetail
import javax.inject.Inject

class GetCharacterByNameUseCase @Inject constructor(
    private val starWarsRepository: StarWarsRepository,
) : UseCase<String, CharacterDetail>() {
    override suspend fun execute(parameters: String): CharacterDetail =
        starWarsRepository.getCharacterByName(parameters)
}
