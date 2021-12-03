package divyansh.tech.animeclassroom.genres

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
import divyansh.tech.animeclassroom.common.utils.EventObserver
import divyansh.tech.animeclassroom.databinding.FragmentGenresBinding
import divyansh.tech.animeclassroom.genres.callbacks.GenreClickCallbacks
import divyansh.tech.animeclassroom.genres.epoxy.GenreController

@AndroidEntryPoint
class GenreFragment: Fragment() {

    private lateinit var _binding: FragmentGenresBinding
    private val binding get() = _binding

    private val viewModel by viewModels<GenreViewModel>()

    private val args by navArgs<GenreFragmentArgs>()

    private val controller by lazy {
        GenreController(GenreClickCallbacks(viewModel))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGenresBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        setupRecyclerView()
        setupObservers()
    }

    private fun setupViews() {
        binding.toolbar.title.text = args.genre
        binding.toolbar.goBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setupRecyclerView() {
        binding.genreRv.apply {
            layoutManager = GridLayoutManager(requireContext(), 3)
            (layoutManager as GridLayoutManager).spanSizeLookup = controller.spanSizeLookup
            adapter = controller.adapter
            controller.spanCount = 3
        }
    }

    private fun setupObservers() {

        viewModel.genreSearchLiveData.observe(
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
        viewModel.searchGenre(args.genre)
    }
}