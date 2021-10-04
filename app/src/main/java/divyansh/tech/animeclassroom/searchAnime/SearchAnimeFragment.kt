package divyansh.tech.animeclassroom.searchAnime

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import divyansh.tech.animeclassroom.EventObserver
import divyansh.tech.animeclassroom.ResultWrapper
import divyansh.tech.animeclassroom.databinding.FragmentSearchBinding
import divyansh.tech.animeclassroom.home.callbacks.HomeScreenCallbacks
import divyansh.tech.animeclassroom.home.callbacks.SearchScreenCallbacks
import divyansh.tech.animeclassroom.searchAnime.epoxy.EpoxySearchController

@AndroidEntryPoint
class SearchAnimeFragment: Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private val viewModel by viewModels<SearchAnimeViewModel>()
    private val searchController by lazy {
        EpoxySearchController(SearchScreenCallbacks(viewModel))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentSearchBinding.inflate(
                inflater,
                container,
                false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupEditText()
        setupClickListeners()
        setupRecyclerView()
        setupObservers()
    }

    private fun setupEditText() {
        binding.searchEditText.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH || event.action == KeyEvent.ACTION_DOWN) {
                hideKeyBoard()
                binding.searchEditText.clearFocus()
                viewModel.searchAnime(v.text.toString().trim())
                return@OnEditorActionListener true
            }
            false
        })
    }

    private fun setupClickListeners() {
        binding.backButton.setOnClickListener {
            hideKeyBoard()
            findNavController().popBackStack()
        }
    }

    private fun setupRecyclerView() {
        binding.searchRecyclerView.apply {
            layoutManager = GridLayoutManager(requireContext(), 3)
            adapter = searchController.adapter
        }
    }


    private fun setupObservers() {

        viewModel.searchAnimeLiveData.observe(
            viewLifecycleOwner,
            Observer {
                Log.d("SearchAnimeFragment", "${it.data}")
                when (it) {
                    is ResultWrapper.Success -> {
                        binding.searchRecyclerView.visibility=View.VISIBLE
                        binding.noResultsView.visibility=View.GONE
                        searchController.setData(it.data)
                    }

                    is ResultWrapper.Error ->{
                        binding.searchRecyclerView.visibility=View.INVISIBLE
                        binding.noResultsView.visibility=View.VISIBLE
                    }
                    else -> {

                    }
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

    private fun hideKeyBoard() {
        val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(activity?.currentFocus?.windowToken, 0)
    }

    private fun showKeyBoard() {
        val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(activity?.currentFocus, 0)
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = activity?.getSystemService(Context.CONNECTIVITY_SERVICE)
        return if (connectivityManager is ConnectivityManager) {
            val networkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo
            networkInfo?.isConnected ?: false
        } else false
    }
}