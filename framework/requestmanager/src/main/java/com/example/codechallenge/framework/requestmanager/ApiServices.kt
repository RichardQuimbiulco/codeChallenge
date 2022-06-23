package com.example.codechallenge.framework.requestmanager

import com.example.codechallenge.framework.requestmanager.ApiConstants.ENDPOINT_CHARACTER
import com.example.codechallenge.framework.requestmanager.ApiConstants.ENDPOINT_CHARACTERS
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CharacterService {

    @GET(ENDPOINT_CHARACTERS)
    fun getAllCharacters(
        @Query("ts") ts: Int,
        @Query("offset") offset: Int,
        @Query("apikey") apikey: String,
        @Query("hash") hash: String,
    ): Single<CharacterResponseServer>

    @GET(ENDPOINT_CHARACTER)
    fun getCharacter(
        @Path("idCharacter") idCharacter: Int,
        @Query("ts") ts: Int,
        @Query("apikey") apikey: String,
        @Query("hash") hash: String,
    ): Single<CharacterResponseServer>
}

