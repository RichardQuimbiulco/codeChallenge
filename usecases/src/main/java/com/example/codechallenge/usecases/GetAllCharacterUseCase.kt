package com.example.codechallenge.usecases

import com.example.codechallenge.data.CharacterRepository
import com.example.codechallenge.domain.Character
import io.reactivex.Single

class GetAllCharacterUseCase(
    private val characterRepository: CharacterRepository
) {
    fun invoke(offset: Int): Single<List<Character>> = characterRepository.getAllCharacters(offset)
}