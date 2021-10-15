package divyansh.tech.animeclassroom.favorites.screens.manga

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import divyansh.tech.animeclassroom.EventObserver
import divyansh.tech.animeclassroom.ResultWrapper
import divyansh.tech.animeclassroom.databinding.FragmentFavoriteMangaBinding
import divyansh.tech.animeclassroom.favorites.screens.manga.epoxy.EpoxyFavoriteMangaController

@AndroidEntryPoint
class FavoriteMangaFragment : Fragment() {

    companion object {
        fun getInstance() = FavoriteMangaFragment()
    }

    private lateinit var _favouriteMangaFragmentBinding: FragmentFavoriteMangaBinding
    private val binding: FragmentFavoriteMangaBinding
        get() = _favouriteMangaFragmentBinding

    private val viewModel by viewModels<FavoriteMangaViewModel>()
    private val favoriteMangaController: EpoxyFavoriteMangaController by lazy {
        EpoxyFavoriteMangaController(FavoriteMangaCallbacks(viewModel))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        _favouriteMangaFragmentBinding = FragmentFavoriteMangaBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.favouriteMangaListState.observe(viewLifecycleOwner) { result ->
            when (result) {
                is ResultWrapper.Success -> {
                    result.data?.let {
                        binding.noMangaText.visibility =
                            if (it.isEmpty()) View.VISIBLE else View.GONE
                        favoriteMangaController.setData(it)
                    }
                }
                is ResultWrapper.Error -> Log.i("FAVORITES", result.message.toString())
                is ResultWrapper.Loading -> {}
            }
        }

        viewModel.navigation.observe(
            viewLifecycleOwner,
            EventObserver{
                findNavController().navigate(it)
            }
        )
    }

    private fun setupRecyclerView() {
        with(binding.favoriteMangaList) {
            layoutManager = GridLayoutManager(requireContext(), 3)
            adapter = favoriteMangaController.adapter
        }
    }

}