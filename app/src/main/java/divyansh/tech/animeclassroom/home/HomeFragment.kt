package divyansh.tech.animeclassroom.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import divyansh.tech.animeclassroom.R
import divyansh.tech.animeclassroom.ResultWrapper
import divyansh.tech.animeclassroom.databinding.FragmentHomeBinding

@AndroidEntryPoint
class HomeFragment: Fragment() {

    private lateinit var _homeFragmentBinding: FragmentHomeBinding
    val binding: FragmentHomeBinding get() = _homeFragmentBinding

    private val viewModel by viewModels<HomeViewModel>()

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
    }

    private fun setupObservers() {
        viewModel.popularAnimeLiveData.observe(
            viewLifecycleOwner,
            Observer {
                when (it) {
                    is ResultWrapper.Success -> Log.i("HOME", it.data.toString())
                    is ResultWrapper.Error -> Log.i("HOME", it.message.toString())
                    is ResultWrapper.Loading -> {}
                }
            }
        )

        viewModel.recentReleaseLiveData.observe(
                viewLifecycleOwner,
                Observer {
                    when (it) {
                        is ResultWrapper.Success -> Log.i("HOME-RECENT", it.data.toString())
                        is ResultWrapper.Error -> Log.i("HOME", it.message.toString())
                        is ResultWrapper.Loading -> {}
                    }
                }
        )
    }
}