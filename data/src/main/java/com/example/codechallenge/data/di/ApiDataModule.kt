package com.example.codechallenge.data.di

import com.example.codechallenge.data.api.CharacterRequest
import com.example.codechallenge.data.api.CharacterRetrofitDataSource
import com.example.codechallenge.data.datasource.RemoteCharacterDataSource
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class ApiDataModule {

    @Provides
    fun remoteCharacterDataSource(characterRequest: CharacterRequest): RemoteCharacterDataSource =
        CharacterRetrofitDataSource(characterRequest)

    @Provides
    fun characterRequestProvider(@Named("baseUrl") baseUrl: String) = CharacterRequest(baseUrl)
}