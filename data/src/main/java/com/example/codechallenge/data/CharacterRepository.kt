package com.example.codechallenge.data

class CharacterRepository(
    private val remoteCharacterDataSource: RemoteCharacterDataSource
) {
    fun getAllCharacters(offset: Int) = remoteCharacterDataSource.getAllCharacters(offset)
    fun getCharacterById(idCharacter: Int) = remoteCharacterDataSource.getCharacterById(idCharacter)
}