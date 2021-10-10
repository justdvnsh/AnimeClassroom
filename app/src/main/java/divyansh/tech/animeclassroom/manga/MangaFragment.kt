package divyansh.tech.animeclassroom.manga

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import divyansh.tech.animeclassroom.Event
import divyansh.tech.animeclassroom.EventObserver
import divyansh.tech.animeclassroom.R
import divyansh.tech.animeclassroom.ResultWrapper
import divyansh.tech.animeclassroom.databinding.FragmentMangaBinding
import divyansh.tech.animeclassroom.manga.callbacks.MangaClickCallback
import divyansh.tech.animeclassroom.manga.epoxy.EpoxyMangaHomeController

@AndroidEntryPoint
class MangaFragment: Fragment() {

    private lateinit var _binding: FragmentMangaBinding
    val binding get() = _binding

    private val viewModel by viewModels<MangaViewModel>()

    private val mangaController by lazy {
        EpoxyMangaHomeController(MangaClickCallback(viewModel))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMangaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        val manager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        manager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_NONE
        binding.mangaRv.apply {
            layoutManager = manager
            mangaController.spanCount = 2
            adapter = mangaController.adapter
            setHasFixedSize(true)
        }
    }

    private fun setupObservers() {
        viewModel.mangaListLiveData.observe(
            viewLifecycleOwner,
            Observer {
                when (it) {
                    is ResultWrapper.Success -> mangaController.setData(it.data)
                    else -> {}
                }
            }
        )

        viewModel.navigation.observe(
            viewLifecycleOwner,
            EventObserver {
                findNavController().navigate(it)
            }
        )
    }
}