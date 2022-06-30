package com.example.codechallenge.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.codechallenge.presentation.Event
import com.example.codechallenge.presentation.itemViewModel.ItemViewModel
import com.example.codechallenge.presentation.itemViewModel.toCharacterViewModelList
import com.example.codechallenge.usecases.GetAllCharacterUseCase
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class CharacterListViewModel(
    private val getAllCharacterUseCase: GetAllCharacterUseCase
) : ViewModel() {

    private val disposable = CompositeDisposable()
    private val _events = MutableLiveData<Event<CharacterListNavigation>>()
    val events: LiveData<Event<CharacterListNavigation>> get() = _events

    private val _navigateCharacterDetail = MutableLiveData<Int>()
    val navigateCharacterDetail: LiveData<Int> get() = _navigateCharacterDetail

    private var offset = 0
    private var isLastPage = false
    private var isLoading = false

    fun onLoadMoreItems(
        visibleItemCount: Int,
        firstVisibleItemPosition: Int,
        totalItemCount: Int
    ) {
        if (isLoading || isLastPage || !isInFooter(
                visibleItemCount,
                firstVisibleItemPosition,
                totalItemCount
            )
        ) {
            return
        }
        offset += PAGE_SIZE
        onGetAllCharacters()
    }

    fun onRetryGetAllCharacter(itemCount: Int) {
        if (itemCount > 0) {
            _events.value = Event(CharacterListNavigation.HideLoading)
            return
        }
        onGetAllCharacters()
    }

    fun onGetAllCharacters() {
        disposable.add(
            getAllCharacterUseCase
                .invoke(offset)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe {
                    _events.value = Event(CharacterListNavigation.ShowLoading)
                }
                .subscribe({ characterList ->
                    if (characterList.size < PAGE_SIZE) {
                        isLastPage = true
                    }
                    _events.value = Event(
                        CharacterListNavigation.ShowCharacterList(
                            characterList.toCharacterViewModelList(::navigateToCharacterDetail)
                        )
                    )
                }, { error ->
                    isLastPage = true
                    _events.value = Event(CharacterListNavigation.HideLoading)
                    _events.value = Event(CharacterListNavigation.ShowCharacterError(error))
                })
        )
    }

    private fun navigateToCharacterDetail(characterId: Int) {
        _navigateCharacterDetail.value = characterId
    }

    fun navigateToCharacterDetailComplete() {
        _navigateCharacterDetail.value = -1
    }

    private fun isInFooter(
        visibleItemCount: Int,
        firstVisibleItemPosition: Int,
        totalItemCount: Int
    ): Boolean {
        return visibleItemCount + firstVisibleItemPosition >= totalItemCount
                && firstVisibleItemPosition >= 0
                && totalItemCount >= PAGE_SIZE
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

    fun restartOffset() {
        offset = 0
    }

    companion object {
        private const val PAGE_SIZE = 20
    }

    sealed class CharacterListNavigation {
        data class ShowCharacterError(val error: Throwable) : CharacterListNavigation()
        data class ShowCharacterList(val characterList: List<ItemViewModel>) :
            CharacterListNavigation()

        object HideLoading : CharacterListNavigation()
        object ShowLoading : CharacterListNavigation()
    }
}