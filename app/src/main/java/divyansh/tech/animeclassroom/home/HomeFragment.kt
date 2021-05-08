package divyansh.tech.animeclassroom.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import divyansh.tech.animeclassroom.R
import divyansh.tech.animeclassroom.ResultWrapper
import divyansh.tech.animeclassroom.databinding.FragmentHomeBinding
import divyansh.tech.animeclassroom.home.callbacks.AnimeClickCallback
import divyansh.tech.animeclassroom.home.epoxy.EpoxyHomeController
import divyansh.tech.animeclassroom.player.PlayerActivity

@AndroidEntryPoint
class HomeFragment: Fragment() {

    private lateinit var _homeFragmentBinding: FragmentHomeBinding
    val binding: FragmentHomeBinding get() = _homeFragmentBinding

    private val viewModel by viewModels<HomeViewModel>()
    private val homeController by lazy {
        EpoxyHomeController(AnimeClickCallback(viewModel))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _homeFragmentBinding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            adapter = homeController.adapter
        }
    }

    private fun setupObservers() {
        viewModel.animeList.observe(
            viewLifecycleOwner,
            Observer {
                when (it) {
                    is ResultWrapper.Success -> homeController.setData(it.data?.sortedBy { it.type })
                    is ResultWrapper.Error -> Log.i("HOME", it.message.toString())
                    is ResultWrapper.Loading -> {}
                }
            }
        )

        viewModel.onAnimeClickedEventLiveData.observe(
                viewLifecycleOwner,
                Observer {
                    Toast.makeText(requireContext(), it.toString(), Toast.LENGTH_SHORT).show()
                }
        )

        viewModel.onGenreClickedEventLiveData.observe(
                viewLifecycleOwner,
                Observer {
                    Toast.makeText(requireContext(), it.toString(), Toast.LENGTH_SHORT).show()
                }
        )

        viewModel.onEpisodeClickedEventLiveData.observe(
                viewLifecycleOwner,
                Observer {
                    startActivity(Intent(requireContext(), PlayerActivity::class.java))
                }
        )
    }
}