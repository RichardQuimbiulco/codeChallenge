package com.example.codechallenge.di

import com.example.codechallenge.presentation.viewmodel.CharacterListViewModel
import com.example.codechallenge.usecases.GetAllCharacterUseCase
import dagger.Module
import dagger.Provides
import dagger.Subcomponent

@Module
class CharacterListModule {

    @Provides
    fun characterListViewModelProvider(getAllCharacterUseCase: GetAllCharacterUseCase) =
        CharacterListViewModel(getAllCharacterUseCase)

}

@Subcomponent(modules = [(CharacterListModule::class)])
interface CharacterListComponent {
    val characterListViewModel: CharacterListViewModel
}