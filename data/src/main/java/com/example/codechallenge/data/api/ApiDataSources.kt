package com.example.codechallenge.data.api

import com.example.codechallenge.data.datasource.RemoteCharacterDataSource
import com.example.codechallenge.framework.requestmanager.ApiConstants
import com.example.codechallenge.framework.requestmanager.CharacterResponseServer
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import com.example.codechallenge.model.Character
import io.reactivex.schedulers.Schedulers

class CharacterRetrofitDataSource(
    private val characterRequest: CharacterRequest
) : RemoteCharacterDataSource {

    override fun getAllCharacters(offset: Int): Single<List<Character>> {
        return characterRequest
            .getService<CharacterService>()
            .getAllCharacters(1, offset, ApiConstants.API_KEY, ApiConstants.HASH)
            .map(CharacterResponseServer::toCharacterDomainList)
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getCharacterById(idCharacter: Int): Single<Character> {
        return characterRequest
            .getService<CharacterService>()
            .getCharacter(idCharacter, 1, ApiConstants.API_KEY, ApiConstants.HASH)
            .map(CharacterResponseServer::toCharacterDomain)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
    }
}