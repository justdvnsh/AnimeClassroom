package divyansh.tech.animeclassroom.favorites.screens.animes

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import divyansh.tech.animeclassroom.common.utils.EventObserver
import divyansh.tech.animeclassroom.common.utils.ResultWrapper
import divyansh.tech.animeclassroom.databinding.FragmentFavoriteAnimeBinding
import divyansh.tech.animeclassroom.favorites.screens.animes.epoxy.EpoxyFavoriteAnimeController

@AndroidEntryPoint
class FavoriteAnimeFragment : Fragment() {

    companion object {
        fun getInstance() = FavoriteAnimeFragment()
    }

    private lateinit var _favoriteAnimeFragmentBinding: FragmentFavoriteAnimeBinding
    private val binding: FragmentFavoriteAnimeBinding
        get() = _favoriteAnimeFragmentBinding

    private val viewModel by viewModels<FavoriteAnimeViewModel>()

    private val favoriteAnimeController: EpoxyFavoriteAnimeController by lazy {
        EpoxyFavoriteAnimeController(FavoriteAnimeCallbacks(viewModel))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _favoriteAnimeFragmentBinding = FragmentFavoriteAnimeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.favoriteAnimeListState.observe(viewLifecycleOwner) { result ->
            when (result) {
                is ResultWrapper.Success -> {
                    result.data?.let {
                        binding.noAnimeText.visibility = if (it.isEmpty()) View.VISIBLE else View.GONE
                        favoriteAnimeController.setData(it)
                    }
                }
                is ResultWrapper.Error -> Log.i("FAVORITES", result.message.toString())
                is ResultWrapper.Loading -> {}
            }

        }
        viewModel.navigation.observe(
            viewLifecycleOwner,
            EventObserver {
                findNavController().navigate(it)
            }
        )
    }

    private fun setupRecyclerView() {
        with(binding.favoriteAnimeList) {
            layoutManager = GridLayoutManager(requireContext(), 3)
            adapter = favoriteAnimeController.adapter
        }
    }
}