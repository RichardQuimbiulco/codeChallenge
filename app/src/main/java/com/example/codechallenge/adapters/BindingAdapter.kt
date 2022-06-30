package com.example.codechallenge.adapters

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.codechallenge.R
import com.example.codechallenge.presentation.Event
import com.example.codechallenge.presentation.viewmodel.CharacterListViewModel
import com.example.codechallenge.utils.bindImageUrl
import com.example.codechallenge.utils.showLongToast
import kotlinx.android.synthetic.main.item_grid_character.view.character_image

@BindingAdapter("itemViewModels")
fun bindItemViewModels(
    recyclerView: RecyclerView,
    events: Event<CharacterListViewModel.CharacterListNavigation>
) {
    val swipe = recyclerView.parent as SwipeRefreshLayout
    events.getContentIfNotHandled()?.let { navigation ->
        when (navigation) {
            CharacterListViewModel.CharacterListNavigation.HideLoading -> {
                swipe.isRefreshing = false
            }
            is CharacterListViewModel.CharacterListNavigation.ShowCharacterError -> recyclerView.context.showLongToast(
                "Error"
            )
            is CharacterListViewModel.CharacterListNavigation.ShowCharacterList -> navigation.run {
                val adapter = getOrCreateAdapter(recyclerView)
                adapter.addData(characterList)
                swipe.isRefreshing = false
            }
            CharacterListViewModel.CharacterListNavigation.ShowLoading -> {
                swipe.isRefreshing = true
            }
        }
    }
}

@BindingAdapter("imageSrc")
fun ImageView.bindImage(imgSrc: String) {
    this.character_image.bindImageUrl(
        url = imgSrc,
        placeholder = R.drawable.ic_camera_alt_black,
        errorPlaceholder = R.drawable.ic_broken_image_black
    )
}

private fun getOrCreateAdapter(recyclerView: RecyclerView): CharacterGridAdapter {
    return if (recyclerView.adapter != null && recyclerView.adapter is CharacterGridAdapter) {
        recyclerView.adapter as CharacterGridAdapter
    } else {
        val characterGridAdapter = CharacterGridAdapter()
        recyclerView.adapter = characterGridAdapter
        characterGridAdapter
    }
}