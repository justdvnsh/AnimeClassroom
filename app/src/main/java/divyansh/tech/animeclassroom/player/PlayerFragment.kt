package divyansh.tech.animeclassroom.player

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.navigation.navArgs
import com.google.android.exoplayer2.DefaultLoadControl
import com.google.android.exoplayer2.DefaultRenderersFactory
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import dagger.hilt.android.AndroidEntryPoint
import divyansh.tech.animeclassroom.R
import divyansh.tech.animeclassroom.ResultWrapper
import divyansh.tech.animeclassroom.databinding.FragmentPlayerBinding
import divyansh.tech.animeclassroom.models.home.PlayerScreenModel

//const val URL: String = "https://storage.googleapis.com/shining-berm-311304/R1R9M7P74XZH/22a_1620705665_4129.mp4"

@AndroidEntryPoint
class PlayerFragment: Fragment() {

    private lateinit var binding: FragmentPlayerBinding
    private lateinit var exoPlayer: ExoPlayer
    private val viewModel by viewModels<PlayerViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPlayerBinding.inflate(
            inflater,
            container,
            false
        )
        viewModel.getStreamingUrl(episodeUrl = (requireActivity() as PlayerActivity).episodeUrl.episodeUrl)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.streamingLiveData.observe(
            viewLifecycleOwner,
            Observer {
                when (it) {
                    is ResultWrapper.Success -> {
                        Log.i("Player-Frag", it.data.toString())
                        it.data?.let { it1 -> initializePlayer(it1) }
                    }
                    else -> {
                        Log.i("Player-Frag", it.toString())
                    }
                }
            }
        )
    }

    private fun initializePlayer(data: PlayerScreenModel) {
        val trackSelector = DefaultTrackSelector()
        val loadControl = DefaultLoadControl()
        val renderersFactory = DefaultRenderersFactory(requireContext())

        exoPlayer = ExoPlayerFactory.newSimpleInstance(requireContext(), renderersFactory, trackSelector, loadControl)
        binding.exoPlayerView.player = exoPlayer
        play(data)
    }

    private fun play(data: PlayerScreenModel) {
        val mediaSource = ExtractorMediaSource
            .Factory(DefaultDataSourceFactory(requireContext(), getString(R.string.user_agent)))
            .setExtractorsFactory(DefaultExtractorsFactory())
            .createMediaSource(Uri.parse(data.streamingUrl))

        exoPlayer.prepare(mediaSource)
        exoPlayer.playWhenReady = true
    }

    override fun onPause() {
        super.onPause()
        exoPlayer.stop()
        exoPlayer.release()
    }

    override fun onStop() {
        super.onStop()
        exoPlayer.stop()
        exoPlayer.release()
    }
}