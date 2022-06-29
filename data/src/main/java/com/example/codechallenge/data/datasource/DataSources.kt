package com.example.codechallenge.data.datasource

import com.example.codechallenge.model.Character
import io.reactivex.Single

interface RemoteCharacterDataSource {
    fun getAllCharacters(offset: Int): Single<List<Character>>
    fun getCharacterById(idCharacter: Int): Single<Character>
}