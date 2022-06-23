package com.example.codechallenge.data

import com.example.codechallenge.domain.Character
import io.reactivex.Single

interface RemoteCharacterDataSource {
    fun getAllCharacters(offset: Int): Single<List<Character>>
    fun getCharacterById(idCharacter: Int): Single<Character>
}