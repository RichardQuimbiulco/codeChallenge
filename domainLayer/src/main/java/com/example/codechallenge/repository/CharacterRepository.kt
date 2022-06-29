package com.example.codechallenge.repository

import com.example.codechallenge.model.Character
import io.reactivex.Single

interface CharacterRepository {
    fun getAllCharacters(offset: Int): Single<List<Character>>
    fun getCharacterById(idCharacter: Int): Single<Character>
}