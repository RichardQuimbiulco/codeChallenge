package com.example.codechallenge.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.codechallenge.R
import com.example.codechallenge.databinding.ItemGridCharacterBinding
import com.example.codechallenge.domain.Character
import com.example.codechallenge.utils.bindImageUrl
import com.example.codechallenge.utils.bindingInflate
import kotlinx.android.synthetic.main.item_grid_character.view.character_image

class CharacterGridAdapter(private val clickListener: CharacterListener) :
RecyclerView.Adapter<CharacterGridAdapter.CharacterGridViewHolder>() {

    private val characterList: MutableList<Character> = mutableListOf()

    fun addData(newData: List<Character>) {
        characterList.addAll(newData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        CharacterGridViewHolder(
            parent.bindingInflate(R.layout.item_grid_character, false)
        )

    override fun getItemCount() = characterList.size

    override fun getItemId(position: Int): Long = characterList[position].id.toLong()

    override fun onBindViewHolder(holder: CharacterGridViewHolder, position: Int) {
        holder.bind(characterList[position], clickListener)
    }

    class CharacterGridViewHolder(
        private val dataBinding: ItemGridCharacterBinding
    ) : RecyclerView.ViewHolder(dataBinding.root) {

        fun bind(item: Character, clickListener: CharacterListener) {
            dataBinding.character = item
            dataBinding.clickListener = clickListener
            itemView.character_image.bindImageUrl(
                url = item.image,
                placeholder = R.drawable.ic_camera_alt_black,
                errorPlaceholder = R.drawable.ic_broken_image_black
            )
        }

    }
}

class CharacterListener(val clickListener: (characterId: Int) -> Unit) {
    fun onClick(character: Character) = clickListener(character.id)
}
