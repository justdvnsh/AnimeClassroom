package divyansh.tech.animeclassroom.manga.screens.mangaDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import divyansh.tech.animeclassroom.Event
import divyansh.tech.animeclassroom.EventObserver
import divyansh.tech.animeclassroom.databinding.FragmentMangaDetailsBinding
import divyansh.tech.animeclassroom.manga.screens.mangaDetail.callbacks.MangaDetailCallbacks
import divyansh.tech.animeclassroom.manga.screens.mangaDetail.epoxy.EpoxyMangaDetailController

@AndroidEntryPoint
class MangaDetailFragment: Fragment() {

    private lateinit var _binding: FragmentMangaDetailsBinding
    val binding get() = _binding

    private val viewModel by viewModels<MangaDetailViewModel>()

    private val args by navArgs<MangaDetailFragmentArgs>()

    private val controller by lazy {
        EpoxyMangaDetailController(MangaDetailCallbacks(viewModel))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMangaDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupObservers()
    }

    private fun setupRecyclerView() {
        binding.mangaDetailRv.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            (layoutManager as GridLayoutManager).spanSizeLookup = controller.spanSizeLookup
            adapter = controller.adapter
            controller.spanCount = 2
        }
    }

    private fun setupObservers() {
        viewModel.mangaDetailLiveData.observe(
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
        viewModel.getMangaDetails(args.url)
    }

}