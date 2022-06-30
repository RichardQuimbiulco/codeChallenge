package com.example.codechallenge.presentation.itemViewModel

import com.example.codechallenge.R
import com.example.codechallenge.model.Character

class CharacterViewModel(
    val id: Int,
    val name: String,
    val image: String,
    private val onItemClick: (Int) -> Unit
) : ItemViewModel {
    override val layoutId: Int = R.layout.item_grid_character

    fun onClick(idCharacter: Int) {
        onItemClick(idCharacter)
    }
}

fun Character.toCharacterViewModel(onItemClick: (Int) -> Unit): CharacterViewModel =
    CharacterViewModel(id = id, name = name, image = image, onItemClick = onItemClick)

fun List<Character>.toCharacterViewModelList(
    onItemClick: (Int) -> Unit
): List<CharacterViewModel> = map { it.toCharacterViewModel(onItemClick) }

