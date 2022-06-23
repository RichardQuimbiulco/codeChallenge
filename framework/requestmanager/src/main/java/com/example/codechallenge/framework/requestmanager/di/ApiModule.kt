package com.example.codechallenge.framework.requestmanager.di

import com.example.codechallenge.data.RemoteCharacterDataSource
import com.example.codechallenge.domain.Character
import com.example.codechallenge.framework.requestmanager.ApiConstants
import com.example.codechallenge.framework.requestmanager.CharacterRequest
import com.example.codechallenge.framework.requestmanager.CharacterRetrofitDataSource
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
class ApiModule {

    @Provides
    fun characterRequestProvider(@Named("baseUrl") baseUrl: String) = CharacterRequest(baseUrl)

    @Provides
    @Singleton
    @Named("baseUrl")
    fun baseUrlProvider(): String = ApiConstants.BASE_API_URL

    @Provides
    fun remoteCharacterDataSource(characterRequest: CharacterRequest): RemoteCharacterDataSource =
        CharacterRetrofitDataSource(characterRequest)

}