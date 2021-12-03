package divyansh.tech.animeclassroom.cartoons.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import divyansh.tech.animeclassroom.common.utils.EventObserver
import divyansh.tech.animeclassroom.cartoons.screens.callback.EpisodeClickCallback
import divyansh.tech.animeclassroom.cartoons.screens.epoxy.EpoxyCartoonEpisodeController
import divyansh.tech.animeclassroom.databinding.FragmentCartoonsEpisodesBinding

@AndroidEntryPoint
class CartoonEpisodeFragment: Fragment() {

    private lateinit var _binding: FragmentCartoonsEpisodesBinding

    private val viewModel by viewModels<CartoonEpisodeViewModel>()

    private val args by navArgs<CartoonEpisodeFragmentArgs>()

    private val controller by lazy {
        EpoxyCartoonEpisodeController(EpisodeClickCallback(viewModel))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartoonsEpisodesBinding.inflate(inflater, container, false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding.mangaToolbar.title.text = args.name
        _binding.mangaToolbar.searchManga.visibility = View.GONE
        setupRecyclerView()
        setupObservers()
    }

    private fun setupRecyclerView() {
        _binding.cartoonEpisodesRv.apply {
            layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
            adapter = controller.adapter
            controller.spanCount = 2
        }
    }

    private fun setupObservers() {
        viewModel.episodesLiveData.observe(
            viewLifecycleOwner,
            Observer {
                controller.setData(it)
            }
        )

        viewModel.navigation.observe(
            viewLifecycleOwner,
            EventObserver {
                findNavController().navigate(it)
            }
        )
    }

    override fun onResume() {
        super.onResume()
        viewModel.getEpisodes(args.cartoonUrl)
    }
}