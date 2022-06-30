package com.example.codechallenge.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.codechallenge.R
import com.example.codechallenge.data.api.CharacterRequest
import com.example.codechallenge.databinding.FragmentCharacterListBinding
import com.example.codechallenge.di.CharacterListComponent
import com.example.codechallenge.di.CharacterListModule
import com.example.codechallenge.framework.requestmanager.ApiConstants.BASE_API_URL
import com.example.codechallenge.presentation.viewmodel.CharacterListViewModel
import com.example.codechallenge.utils.app
import com.example.codechallenge.utils.getViewModel
import com.example.codechallenge.utils.setItemDecorationSpacing
import kotlinx.android.synthetic.main.fragment_character_list.rvCharacterList
import kotlinx.android.synthetic.main.fragment_character_list.srwCharacterList

class CharacterListFragment : Fragment() {

    private lateinit var characterRequest: CharacterRequest
    private lateinit var binding: FragmentCharacterListBinding

    private lateinit var characterListComponent: CharacterListComponent

    private val viewModel: CharacterListViewModel by lazy {
        getViewModel { characterListComponent.characterListViewModel }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        characterListComponent = requireContext().app.component.inject(CharacterListModule())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        characterRequest = CharacterRequest(BASE_API_URL)
        binding = FragmentCharacterListBinding.inflate(inflater)
            .apply { lifecycleOwner = viewLifecycleOwner }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        rvCharacterList.run {
            addOnScrollListener(onScrollListener)
            setItemDecorationSpacing(resources.getDimension(R.dimen.list_item_padding))
        }
        srwCharacterList.setOnRefreshListener {
            viewModel.onRetryGetAllCharacter(rvCharacterList.adapter?.itemCount ?: 0)
        }
        viewModel.navigateCharacterDetail.observe(viewLifecycleOwner) {
            if (it != -1) {
                findNavController().navigate(
                    CharacterListFragmentDirections.actionCharacterListFragmentToCharacterDetailFragment(
                        it
                    )
                )
                viewModel.navigateToCharacterDetailComplete()
            }
        }
        viewModel.onGetAllCharacters()
    }

    private val onScrollListener: RecyclerView.OnScrollListener by lazy {
        object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = recyclerView.layoutManager as GridLayoutManager
                val visibleItemCount: Int = layoutManager.childCount
                val totalItemCount: Int = layoutManager.itemCount
                val firstVisibleItemPosition: Int = layoutManager.findFirstVisibleItemPosition()

                viewModel.onLoadMoreItems(
                    visibleItemCount,
                    firstVisibleItemPosition,
                    totalItemCount
                )
            }
        }
    }

    override fun onStop() {
        super.onStop()
        viewModel.restartOffset()
    }
}
