package divyansh.tech.animeclassroom.player

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavDirections
import androidx.navigation.fragment.navArgs
import androidx.navigation.navArgs
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.hls.HlsDataSourceFactory
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import com.google.android.exoplayer2.upstream.HttpDataSource
import dagger.hilt.android.AndroidEntryPoint
import divyansh.tech.animeclassroom.R
import divyansh.tech.animeclassroom.ResultWrapper
import divyansh.tech.animeclassroom.databinding.FragmentPlayerBinding
import divyansh.tech.animeclassroom.home.HomeFragmentDirections
import divyansh.tech.animeclassroom.models.home.PlayerScreenModel
import divyansh.tech.animeclassroom.player.callbacks.PlayerControlListener
import kotlinx.android.synthetic.main.exo_player_custom_controls.*
import kotlinx.android.synthetic.main.exo_player_custom_controls.view.*
import kotlinx.android.synthetic.main.fragment_player.*

@AndroidEntryPoint
class PlayerFragment: Fragment(), PlayerControlListener {

    private lateinit var binding: FragmentPlayerBinding
    private lateinit var exoPlayer: SimpleExoPlayer
    private val viewModel by activityViewModels<PlayerViewModel>()

    private var isFullScreen = false

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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
        setupListeners()
    }

    private fun setupListeners() {
        back.setOnClickListener { onButtonClicked(PlayerViewModel.PlayerClick.BACK) }
        exo_track_selection_view.setOnClickListener { onButtonClicked(PlayerViewModel.PlayerClick.QUALITY_CONTROL) }
        exo_speed_selection_view.setOnClickListener { onButtonClicked(PlayerViewModel.PlayerClick.SPEED_CONTROL) }
        exo_full_Screen.setOnClickListener { onButtonClicked(PlayerViewModel.PlayerClick.FULLSCREEN_TOGGLE) }
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
        exoPlayer.prepare(buildMediaSource(data.streamingUrl.toUri()))
        exoPlayer.playWhenReady = true
    }

    private fun buildMediaSource(uri: Uri): MediaSource {


        val lastPath = uri.lastPathSegment
        val defaultDataSourceFactory =
            DefaultHttpDataSourceFactory("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.122 Safari/537.36")

        if (lastPath!!.contains("m3u8")) {
            return HlsMediaSource.Factory(
                HlsDataSourceFactory {
                    val dataSource: HttpDataSource =
                        DefaultHttpDataSource("Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/93.0.4577.82 Mobile Safari/537.36")
                    dataSource.setRequestProperty("Referer", "https://goload.one")
                    dataSource
                })
                .setAllowChunklessPreparation(true)
                .createMediaSource(uri)
        } else {
//            val dashChunkSourceFactory = DefaultDashChunkSource.Factory(defaultDataSourceFactory)
            return ExtractorMediaSource.Factory(defaultDataSourceFactory)
                .createMediaSource(uri)
        }

    }

    private fun setupObservers() {
        viewModel.streamingLiveData.observe(
                viewLifecycleOwner,
                Observer {
                    when (it) {
                        is ResultWrapper.Success -> {
                            Log.i("Player-Frag", it.data.toString())
                            it.data?.let { it1 ->
                                initializePlayer(it1)
                                if (it1.nextEpisodeUrl == null || it1.nextEpisodeUrl.equals("null"))
                                    binding.exoPlayerView.nextEpisode.visibility = View.GONE
                                if (it1.previousEpisodeUrl == null || it1.previousEpisodeUrl.equals("null"))
                                    binding.exoPlayerView.previousEpisode.visibility = View.GONE
                                episodeName.text = it1.animeName
                                nextEpisode.setOnClickListener { it1.nextEpisodeUrl?.let { it2 ->
                                    onEpisodeClicked(
                                        it2
                                    )
                                } }
                                previousEpisode.setOnClickListener { it1.previousEpisodeUrl?.let { it2 ->
                                    onEpisodeClicked(
                                        it2
                                    )
                                } }
                            }
                        }
                        else -> {
                            Log.i("Player-Frag", it.toString())
                        }
                    }
                }
        )

        viewModel.clickControlLiveData.observe(
                viewLifecycleOwner,
                Observer {
                    when(it) {
                        PlayerViewModel.PlayerClick.BACK ->
                            requireActivity().finish()
                        PlayerViewModel.PlayerClick.QUALITY_CONTROL -> Toast.makeText(requireContext(), "QUALITY", Toast.LENGTH_SHORT).show()
                        PlayerViewModel.PlayerClick.SPEED_CONTROL -> Toast.makeText(requireContext(), "SPEED", Toast.LENGTH_SHORT).show()
                        PlayerViewModel.PlayerClick.FULLSCREEN_TOGGLE -> toggleFullScreen()
                    }
                }
        )
    }

    override fun onPause() {
        super.onPause()
        releasePlayer()
    }

    override fun onStop() {
        super.onStop()
        releasePlayer()
    }

    private fun releasePlayer() {
        exoPlayer.stop()
        exoPlayer.release()
    }

    /*
    * Method Selections
    * */
    private fun toggleFullScreen() {
        if (isFullScreen) {
            exoPlayerFrameLayout.resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FILL
            exoPlayerView.resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FILL
            isFullScreen = false
            exo_full_Screen
                    .setImageDrawable(
                            ContextCompat.getDrawable(
                                    requireContext(),
                                    R.drawable.exo_controls_fullscreen_exit
                            )
                    )
        } else {
            exoPlayerFrameLayout.resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FIXED_HEIGHT
            exoPlayerView.resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FIT
            isFullScreen = true
            exo_full_Screen
                    .setImageDrawable(
                            ContextCompat.getDrawable(
                                    requireContext(),
                                    R.drawable.exo_controls_fullscreen_enter
                            )
                    )
        }
    }

    override fun onButtonClicked(item: PlayerViewModel.PlayerClick) {
        viewModel.updateButtonClick(item)
    }

    override fun onEpisodeClicked(episodeUrl: String) {
        exoPlayer.playWhenReady = false
        (requireActivity() as PlayerActivity).updateEpisode(episodeUrl)
    }
}