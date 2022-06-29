package com.example.codechallenge.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.codechallenge.model.Character
import com.example.codechallenge.presentation.Event
import com.example.codechallenge.usecases.GetDetailCharacterUseCase
import io.reactivex.disposables.CompositeDisposable

class CharacterDetailViewModel(
    private val getDetailCharacterUseCase: GetDetailCharacterUseCase
) : ViewModel() {

    private val disposable = CompositeDisposable()

    private val _events = MutableLiveData<Event<CharacterDetailNavigation>>()
    val events: LiveData<Event<CharacterDetailNavigation>> get() = _events

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

    fun onShowCharacter(idCharacter: Int) {
        disposable.add(
            getDetailCharacterUseCase.invoke(idCharacter)
                .subscribe({ characterResponse ->
                    _events.value =
                        Event(CharacterDetailNavigation.ShowCharacter(characterResponse))
                }, { error ->
                    _events.value = Event(CharacterDetailNavigation.ShowCharacterError(error))
                })
        )
    }

    sealed class CharacterDetailNavigation {
        data class ShowCharacter(val character: Character) : CharacterDetailNavigation()
        data class ShowCharacterError(val error: Throwable) : CharacterDetailNavigation()
    }

}