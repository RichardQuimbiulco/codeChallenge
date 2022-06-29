package com.example.codechallenge.data.repository

import com.example.codechallenge.data.datasource.RemoteCharacterDataSource
import com.example.codechallenge.repository.CharacterRepository

class CharacterRepositoryImpl(
    private val remoteCharacterDataSource: RemoteCharacterDataSource
) : CharacterRepository {
    override fun getAllCharacters(offset: Int) = remoteCharacterDataSource.getAllCharacters(offset)
    override fun getCharacterById(idCharacter: Int) =
        remoteCharacterDataSource.getCharacterById(idCharacter)
}