package com.example.codechallenge.usecases

import com.example.codechallenge.model.Character
import com.example.codechallenge.repository.CharacterRepository
import io.reactivex.Single

class GetAllCharacterUseCase(
    private val characterRepository: CharacterRepository
) {
    fun invoke(offset: Int): Single<List<Character>> = characterRepository.getAllCharacters(offset)
}