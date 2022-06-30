package com.example.codechallenge.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.codechallenge.BR
import com.example.codechallenge.R
import com.example.codechallenge.databinding.ItemGridCharacterBinding
import com.example.codechallenge.presentation.itemViewModel.ItemViewModel
import com.example.codechallenge.utils.bindingInflate

class CharacterGridAdapter : RecyclerView.Adapter<CharacterGridAdapter.CharacterGridViewHolder>() {

    private var characterList: MutableList<ItemViewModel> = mutableListOf()

    fun addData(newData: List<ItemViewModel>?) {
        characterList.addAll(newData ?: emptyList())
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        CharacterGridViewHolder(
            parent.bindingInflate(R.layout.item_grid_character, false)
        )

    override fun getItemCount() = characterList.size

    override fun onBindViewHolder(holder: CharacterGridViewHolder, position: Int) {
        holder.bind(characterList[position])
    }

    class CharacterGridViewHolder(
        private val dataBinding: ItemGridCharacterBinding
    ) : RecyclerView.ViewHolder(dataBinding.root) {

        fun bind(itemViewModel: ItemViewModel) {
            dataBinding.setVariable(BR.itemViewModel, itemViewModel)
        }
    }
}
