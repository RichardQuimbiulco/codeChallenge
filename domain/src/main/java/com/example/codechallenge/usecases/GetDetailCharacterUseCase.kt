package com.example.codechallenge.usecases

import com.example.codechallenge.model.Character
import com.example.codechallenge.repository.CharacterRepository
import io.reactivex.Single

class GetDetailCharacterUseCase(private val characterRepository: CharacterRepository) {

    fun invoke(idCharacter: Int): Single<Character> =
        characterRepository.getCharacterById(idCharacter)
}