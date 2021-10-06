package divyansh.tech.animeclassroom.home

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
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import divyansh.tech.animeclassroom.EventObserver
import divyansh.tech.animeclassroom.R
import divyansh.tech.animeclassroom.ResultWrapper
import divyansh.tech.animeclassroom.databinding.FragmentHomeBinding
import divyansh.tech.animeclassroom.home.callbacks.HomeScreenCallbacks
import divyansh.tech.animeclassroom.home.epoxy.EpoxyHomeController

@AndroidEntryPoint
class HomeFragment: Fragment() {

    private lateinit var _homeFragmentBinding: FragmentHomeBinding
    val binding: FragmentHomeBinding get() = _homeFragmentBinding

    private val viewModel by viewModels<HomeViewModel>()
    private val homeController by lazy {
        EpoxyHomeController(HomeScreenCallbacks(viewModel))
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
        setupListeners()
        setupObservers()
        setupRecyclerView()
    }

    private fun setupListeners() {
        binding.search.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
        }
    }

    private fun setupRecyclerView() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireActivity(), RecyclerView.VERTICAL, false)
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

        viewModel.navigation.observe(
            viewLifecycleOwner,
            EventObserver {
                findNavController().navigate(it)
            }
        )
    }
    companion object{
        private const val TAG = "HomeFragment"
    }
}