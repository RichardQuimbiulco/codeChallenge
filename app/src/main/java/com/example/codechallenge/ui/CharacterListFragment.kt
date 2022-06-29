package com.example.codechallenge.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.codechallenge.presentation.viewmodel.CharacterListViewModel
import com.example.codechallenge.R
import com.example.codechallenge.adapters.CharacterGridAdapter
import com.example.codechallenge.adapters.CharacterListener
import com.example.codechallenge.framework.requestmanager.ApiConstants.BASE_API_URL
import com.example.codechallenge.databinding.FragmentCharacterListBinding
import com.example.codechallenge.di.CharacterListComponent
import com.example.codechallenge.di.CharacterListModule
import com.example.codechallenge.framework.requestmanager.CharacterRequest
import com.example.codechallenge.utils.app
import com.example.codechallenge.utils.getViewModel
import com.example.codechallenge.utils.setItemDecorationSpacing
import com.example.codechallenge.utils.showLongToast
import kotlinx.android.synthetic.main.fragment_character_list.rvCharacterList
import kotlinx.android.synthetic.main.fragment_character_list.srwCharacterList

class CharacterListFragment : Fragment() {

    private lateinit var characterRequest: CharacterRequest
    private lateinit var characterGridAdapter: CharacterGridAdapter

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
        return DataBindingUtil.inflate<FragmentCharacterListBinding>(
            inflater,
            R.layout.fragment_character_list,
            container,
            false
        ).apply {
            lifecycleOwner = viewLifecycleOwner
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        characterGridAdapter = CharacterGridAdapter(CharacterListener { characterId ->
            findNavController().navigate(
                CharacterListFragmentDirections.actionCharacterListFragmentToCharacterDetailFragment(
                    characterId
                )
            )
        })
        characterGridAdapter.setHasStableIds(true)
        rvCharacterList.run {
            addOnScrollListener(onScrollListener)
            setItemDecorationSpacing(resources.getDimension(R.dimen.list_item_padding))
            adapter = characterGridAdapter
        }
        srwCharacterList.setOnRefreshListener {
            viewModel.onRetryGetAllCharacter(rvCharacterList.adapter?.itemCount ?: 0)
        }
        viewModel.events.observe(viewLifecycleOwner) { events ->
            events?.getContentIfNotHandled()?.let { navigation ->
                when (navigation) {
                    CharacterListViewModel.CharacterListNavigation.HideLoading -> srwCharacterList.isRefreshing =
                        false
                    is CharacterListViewModel.CharacterListNavigation.ShowCharacterError -> requireContext().showLongToast(
                        "Error"
                    )
                    is CharacterListViewModel.CharacterListNavigation.ShowCharacterList -> navigation.run {
                        characterGridAdapter.addData(
                            characterList
                        )
                    }
                    CharacterListViewModel.CharacterListNavigation.ShowLoading -> srwCharacterList.isRefreshing =
                        true
                }
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
