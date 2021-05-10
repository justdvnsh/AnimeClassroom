package divyansh.tech.animeclassroom.animeDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import divyansh.tech.animeclassroom.ResultWrapper
import divyansh.tech.animeclassroom.databinding.FragmentAnimeDetailsBinding

class AnimeDetailFragment: Fragment() {

    private lateinit var binding: FragmentAnimeDetailsBinding
    private val animeModel: AnimeDetailFragmentArgs by navArgs()
    private val viewModel by viewModels<AnimeDetailViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAnimeDetailsBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
        setupRecyclerView()
    }

    private fun setupObservers() {
        viewModel.animeDetailLiveData.observe(
            viewLifecycleOwner,
            Observer {
                when (it) {
                    is ResultWrapper.Success -> {}
                    is ResultWrapper.Error -> {}
                    is ResultWrapper.Loading -> {}
                }
            }
        )
    }

    private fun setupRecyclerView() {}

    override fun onResume() {
        super.onResume()
        viewModel.getAnimeDetails(animeModel.AnimeModel)
    }
}