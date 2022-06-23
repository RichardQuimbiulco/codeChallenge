package com.example.codechallenge.di;

import com.example.codechallenge.presentation.viewmodel.CharacterDetailViewModel
import com.example.codechallenge.usecases.GetDetailCharacterUseCase;

import dagger.Module
import dagger.Provides
import dagger.Subcomponent

@Module
class CharacterDetailModule {

    @Provides
    fun characterDetailViewModelProvider(getDetailCharacterUseCase: GetDetailCharacterUseCase) =
        CharacterDetailViewModel(getDetailCharacterUseCase)

}

@Subcomponent(modules = [(CharacterDetailModule::class)])
interface CharacterDetailComponent {
    val characterDetailViewModel: CharacterDetailViewModel
}