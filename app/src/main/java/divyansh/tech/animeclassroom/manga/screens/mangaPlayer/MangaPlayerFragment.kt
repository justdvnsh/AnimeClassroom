package divyansh.tech.animeclassroom.manga.screens.mangaPlayer

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
import dagger.hilt.android.AndroidEntryPoint
import divyansh.tech.animeclassroom.common.utils.EventObserver
import divyansh.tech.animeclassroom.databinding.FragmentMangaPlayerBinding
import divyansh.tech.animeclassroom.manga.screens.mangaPlayer.epoxy.EpoxyMangaPlayerController

@AndroidEntryPoint
class MangaPlayerFragment: Fragment() {

    private lateinit var _binding: FragmentMangaPlayerBinding
    val binding get() = _binding

    private val viewModel by viewModels<MangaPlayerViewModel>()

    private val args by navArgs<MangaPlayerFragmentArgs>()

    private val controller by lazy {
        EpoxyMangaPlayerController()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMangaPlayerBinding.inflate(inflater, container, false)
        binding.mangaToolbar.title.text = args.mangaName + " - " + args.chapterName
        binding.mangaToolbar.goBack.setOnClickListener { findNavController().popBackStack() }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupObservers()
    }

    private fun setupRecyclerView() {
        binding.mangaPlayerRv.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = controller.adapter
        }
    }

    private fun setupObservers() {

        viewModel.mangaChapterItemLiveData.observe(
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
        viewModel.getChapterItems(args.chapterUrl)
    }
}