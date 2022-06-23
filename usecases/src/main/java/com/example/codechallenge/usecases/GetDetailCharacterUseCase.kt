package com.example.codechallenge.usecases

import com.example.codechallenge.data.CharacterRepository
import com.example.codechallenge.domain.Character
import io.reactivex.Single

class GetDetailCharacterUseCase(private val characterRepository: CharacterRepository) {

    fun invoke(idCharacter: Int): Single<Character> =
        characterRepository.getCharacterById(idCharacter)
}