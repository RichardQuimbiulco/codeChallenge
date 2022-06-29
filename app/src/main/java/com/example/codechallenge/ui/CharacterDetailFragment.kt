package com.example.codechallenge.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.example.codechallenge.presentation.viewmodel.CharacterDetailViewModel
import com.example.codechallenge.R
import com.example.codechallenge.data.api.CharacterRequest
import com.example.codechallenge.databinding.FragmentCharacterDetailBinding
import com.example.codechallenge.di.CharacterDetailComponent
import com.example.codechallenge.di.CharacterDetailModule
import com.example.codechallenge.framework.requestmanager.ApiConstants
import com.example.codechallenge.presentation.Event
import com.example.codechallenge.utils.app
import com.example.codechallenge.utils.bindImageUrl
import com.example.codechallenge.utils.getViewModel
import com.example.codechallenge.utils.showLongToast
import kotlinx.android.synthetic.main.fragment_character_detail.characterImage

class CharacterDetailFragment : Fragment() {

    private lateinit var binding: FragmentCharacterDetailBinding
    private lateinit var characterRequest: CharacterRequest

    private lateinit var characterDetailComponent: CharacterDetailComponent
    private val viewModel: CharacterDetailViewModel by lazy {
        getViewModel {
            characterDetailComponent.characterDetailViewModel
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        characterDetailComponent = requireContext().app.component.inject(CharacterDetailModule())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        characterRequest = CharacterRequest(ApiConstants.BASE_API_URL)
        binding = FragmentCharacterDetailBinding.inflate(inflater)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args = arguments?.let { CharacterDetailFragmentArgs.fromBundle(it) }
        args?.characterId?.let { viewModel.onShowCharacter(it) }
        viewModel.events.observe(viewLifecycleOwner, Observer(this::validateEvents))
    }

    private fun validateEvents(event: Event<CharacterDetailViewModel.CharacterDetailNavigation>) {
        event.getContentIfNotHandled()?.let { navigation ->
            when (navigation) {
                is CharacterDetailViewModel.CharacterDetailNavigation.ShowCharacter -> navigation.run {
                    binding.character = character
                    characterImage.bindImageUrl(
                        character.image,
                        R.drawable.ic_camera_alt_black,
                        R.drawable.ic_broken_image_black
                    )
                }
                is CharacterDetailViewModel.CharacterDetailNavigation.ShowCharacterError -> requireContext().showLongToast(
                    "Woops...!!! Error occurred"
                )
            }
        }
    }

}