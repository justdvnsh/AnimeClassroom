package divyansh.tech.animeclassroom.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import divyansh.tech.animeclassroom.R
import divyansh.tech.animeclassroom.ResultWrapper
import divyansh.tech.animeclassroom.databinding.FragmentHomeBinding
import divyansh.tech.animeclassroom.home.epoxy.EpoxyHomeController

@AndroidEntryPoint
class HomeFragment: Fragment() {

    private lateinit var _homeFragmentBinding: FragmentHomeBinding
    val binding: FragmentHomeBinding get() = _homeFragmentBinding

    private val viewModel by viewModels<HomeViewModel>()
    private val homeController by lazy { EpoxyHomeController() }

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
            layoutManager = LinearLayoutManager(requireContext())
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
    }
}