package com.example.codechallenge.di

import com.example.codechallenge.repository.CharacterRepository
import com.example.codechallenge.usecases.GetAllCharacterUseCase
import com.example.codechallenge.usecases.GetDetailCharacterUseCase
import dagger.Module
import dagger.Provides

@Module
class UseCaseModule {

    @Provides
    fun getAllCharacterUseCaseProvider(characterRepository: CharacterRepository) =
        GetAllCharacterUseCase(characterRepository)

    @Provides
    fun getDetailCharacterUseCaseProvider(characterRepository: CharacterRepository) =
        GetDetailCharacterUseCase(characterRepository)
}