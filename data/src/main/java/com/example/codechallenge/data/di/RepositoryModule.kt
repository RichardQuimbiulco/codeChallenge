package com.example.codechallenge.data.di

import com.example.codechallenge.data.CharacterRepository
import com.example.codechallenge.data.RemoteCharacterDataSource
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {

    @Provides
    fun characterRepositoryProvider(remoteCharacterDataSource: RemoteCharacterDataSource) =
        CharacterRepository(remoteCharacterDataSource)
}