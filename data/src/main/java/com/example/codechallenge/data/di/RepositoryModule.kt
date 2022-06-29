package com.example.codechallenge.data.di

import com.example.codechallenge.data.datasource.RemoteCharacterDataSource
import com.example.codechallenge.data.repository.CharacterRepositoryImpl
import com.example.codechallenge.repository.CharacterRepository
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {

    @Provides
    fun characterRepositoryProvider(remoteCharacterDataSource: RemoteCharacterDataSource): CharacterRepository =
        CharacterRepositoryImpl(remoteCharacterDataSource)
}