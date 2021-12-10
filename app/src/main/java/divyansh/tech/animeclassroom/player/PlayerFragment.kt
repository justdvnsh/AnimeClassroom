package divyansh.tech.animeclassroom.player

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.pm.ActivityInfo
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast

import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.Player.*
import com.google.android.exoplayer2.audio.AudioAttributes
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.source.hls.HlsDataSourceFactory
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import com.google.android.exoplayer2.upstream.HttpDataSource
import dagger.hilt.android.AndroidEntryPoint
import divyansh.tech.animeclassroom.R
import divyansh.tech.animeclassroom.animeDetail.callbacks.EpisodeClickCallback
import divyansh.tech.animeclassroom.common.AnimeClickCallback
import divyansh.tech.animeclassroom.common.utils.ResultWrapper
import divyansh.tech.animeclassroom.common.data.PlayerScreenModel
import divyansh.tech.animeclassroom.common.utils.Event
import divyansh.tech.animeclassroom.common.utils.EventObserver
import divyansh.tech.animeclassroom.databinding.VideoPlayerFragmentBinding
import divyansh.tech.animeclassroom.player.callbacks.PlayerControlListener
import divyansh.tech.animeclassroom.player.epoxy.EpoxyPlayerController
import kotlinx.android.synthetic.main.exo_player_custom_controls.*
import kotlinx.android.synthetic.main.exo_player_custom_controls.view.*
import kotlinx.android.synthetic.main.video_player_fragment.view.*

@AndroidEntryPoint
class PlayerFragment: Fragment(), PlayerControlListener {

    companion object {
        const val URL = "url"
        fun newInstance(arguments: Bundle): PlayerFragment {
            val fragment = PlayerFragment()
            fragment.arguments = arguments
            return fragment
        }
    }

    private lateinit var binding: VideoPlayerFragmentBinding
    private lateinit var exoPlayer: SimpleExoPlayer
    private val viewModel by activityViewModels<PlayerViewModel>()
    private lateinit var exoPlayerView: PlayerView
    private lateinit var exoPlayerFrameLayout: AspectRatioFrameLayout
    private lateinit var parentFragment: VideoDetailFragment

    private var isFullScreen = false

    override fun onAttach(context: Context) {
        super.onAttach(context)
        parentFragment = requireParentFragment() as VideoDetailFragment
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = VideoPlayerFragmentBinding.inflate(
            inflater,
            container,
            false
        )
        exoPlayerView = binding.exoPlayerView
        exoPlayerFrameLayout = binding.exoPlayerFrameLayout
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
        setupObservers()
    }

    private fun setupListeners() {
        back.setOnClickListener { onButtonClicked(PlayerViewModel.PlayerClick.BACK) }
        exo_track_selection_view.setOnClickListener { onButtonClicked(PlayerViewModel.PlayerClick.QUALITY_CONTROL) }
        exo_speed_selection_view.setOnClickListener { onButtonClicked(PlayerViewModel.PlayerClick.SPEED_CONTROL) }
        exo_full_Screen.setOnClickListener { onButtonClicked(PlayerViewModel.PlayerClick.FULLSCREEN_TOGGLE) }

        binding.retry.setOnClickListener {
            if (exoPlayer.playbackError != null)
                exoPlayer.retry()
        }
    }

    private fun initializePlayer(data: String?) {
        val trackSelector = DefaultTrackSelector()
        val loadControl = DefaultLoadControl()
        val renderersFactory = DefaultRenderersFactory(requireContext())

        exoPlayer = ExoPlayerFactory.newSimpleInstance(
            requireContext(),
            renderersFactory,
            trackSelector,
            loadControl
        )

        exoPlayer.addListener(object : EventListener {
            override fun onPlayerError(error: ExoPlaybackException?) {
                super.onPlayerError(error)
                when (error?.type) {
                    ExoPlaybackException.TYPE_OUT_OF_MEMORY -> {
                        exoplayerErrorLayoutChange(
                            visible = true,
                            image = R.drawable.ic_general_error,
                            errorText = R.string.status_code_default
                        )
                    }
                    ExoPlaybackException.TYPE_REMOTE -> {
                        exoplayerErrorLayoutChange(
                            visible = true,
                            image = R.drawable.ic_general_error,
                            errorText = R.string.server_error
                        )
                    }
                    ExoPlaybackException.TYPE_RENDERER -> {
                        exoplayerErrorLayoutChange(
                            visible = true,
                            image = R.drawable.ic_general_error,
                            errorText = R.string.status_code_default
                        )
                    }
                    ExoPlaybackException.TYPE_SOURCE -> {
                        exoplayerErrorLayoutChange(
                            visible = true,
                            image = R.drawable.ic_no_internet,
                            errorText = R.string.no_internet
                        )

                    }
                    ExoPlaybackException.TYPE_UNEXPECTED -> {
                        exoplayerErrorLayoutChange(
                            visible = true,
                            image = R.drawable.ic_general_error,
                            errorText = R.string.status_code_default
                        )
                    }
                }
            }

            override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
                super.onPlayerStateChanged(playWhenReady, playbackState)
                when (playbackState) {
                    STATE_IDLE -> {

                    }
                    STATE_BUFFERING -> {
                        exoplayerErrorLayoutChange(visible = false)
                        exoPlayerView.exo_play.setImageResource(0)
                        exoPlayerView.exo_pause.setImageResource(0)
                    }
                    STATE_READY -> {
                        setEnabledStatusOfPreviousAndNextEpisodesButtons(true)
                        exoPlayerView.videoSurfaceView.visibility = View.VISIBLE
                        exoplayerErrorLayoutChange(visible = false)
                        exoPlayerView.exo_play.setImageResource(R.drawable.ic_media_play)
                        exoPlayerView.exo_pause.setImageResource(R.drawable.ic_media_pause)
                    }
                    STATE_ENDED -> {
                        exoplayerErrorLayoutChange(visible = false)

                    }
                }
            }

        })
        exoPlayerView.player = exoPlayer
        data?.let {
            play(it)
        }
    }

    private fun exoplayerErrorLayoutChange(
        visible: Boolean,
        image: Int = R.drawable.ic_no_internet,
        errorText: Int = R.string.no_internet
    ) {
        binding.errorLayout.isVisible = visible
        binding.errorImage.setImageResource(image)
        binding.errorText.text = getString(errorText)
    }

    private fun play(data: String) {
        if (data.contains("m3u8")) exoPlayer.prepare(buildMediaSource(data.toUri()))
        else {
            if (data.contains("https"))
                exoPlayer.prepare(buildMediaSource(data.toUri()))
            else
                exoPlayer.prepare(buildMediaSource(data.replace("http", "https").toUri()))

        }
        exoPlayer.playWhenReady = true
        audioFocus()
    }

    private fun buildMediaSource(uri: Uri): MediaSource {


        val lastPath = uri.lastPathSegment
        Log.i("PLAYER -> ", lastPath.toString())
        val defaultDataSourceFactory =
            DefaultHttpDataSourceFactory("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.122 Safari/537.36")

        return if (lastPath!!.contains("m3u8")) {
            HlsMediaSource.Factory(
                HlsDataSourceFactory {
                    val dataSource: HttpDataSource =
                        DefaultHttpDataSource("Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/93.0.4577.82 Mobile Safari/537.36")
                    dataSource.setRequestProperty("origin", "https://gogoplay1.com")
                    dataSource.setRequestProperty(
                        "Referer", "https://gogoplay1.com/streaming.php?id=MTc0OTQ1&title=Boruto%3A+Naruto+Next+Generations+Episode+225"
                    )
                    dataSource
                })
                .setAllowChunklessPreparation(true)
                .createMediaSource(uri)
        } else {
//            val dashChunkSourceFactory = DefaultDashChunkSource.Factory(defaultDataSourceFactory)
            ProgressiveMediaSource.Factory(defaultDataSourceFactory)
                .createMediaSource(uri)
        }
    }

    private fun setupObservers() {
        viewModel.streamingLiveData.observe(
            viewLifecycleOwner,
            Observer { result ->
                when(result) {
                    is ResultWrapper.Error -> {}
                    is ResultWrapper.Loading -> setEnabledStatusOfPreviousAndNextEpisodesButtons(false)
                    is ResultWrapper.Success -> {
                        result.data?.let {
                            initializePlayer(it.streamingUrl)
                            setupViews(it)
                            setEnabledStatusOfPreviousAndNextEpisodesButtons(true)
                        }
                    }
                }
            }
        )

        viewModel.speedControlLiveData.observe(
            viewLifecycleOwner,
            EventObserver {
                exoPlayer.playbackParameters = it
                exo_speed_selection_view.text = it.speed.toString()
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

    private fun audioFocus(){
        val audioAttributes: AudioAttributes = AudioAttributes.Builder()
            .setUsage(C.USAGE_MEDIA)
            .setContentType(C.CONTENT_TYPE_MOVIE)
            .build()
        exoPlayer.setAudioAttributes(audioAttributes, true)
    }

    private fun setEnabledStatusOfPreviousAndNextEpisodesButtons(isEnabled: Boolean) {
        nextEpisode.isEnabled = isEnabled
        previousEpisode.isEnabled = isEnabled
    }

    override fun onButtonClicked(item: PlayerViewModel.PlayerClick) {
        viewModel.updateButtonClick(item)
    }

    override fun onEpisodeClicked(episodeUrl: String) {
        exoPlayer.playWhenReady = false
        (requireActivity() as PlayerActivity).updateEpisode(episodeUrl, 0)
    }

    override fun onResume() {
        super.onResume()
    }

    private fun setupViews(it1: PlayerScreenModel) {
        val nextEpisodeButtonVisibility = if (it1.nextEpisodeUrl == null || it1.nextEpisodeUrl.equals("null")) {
            View.GONE
        } else {
            View.VISIBLE
        }
        exoPlayerView.nextEpisode.visibility = nextEpisodeButtonVisibility
        val previousEpisodeButtonVisibility = if (it1.previousEpisodeUrl == null || it1.previousEpisodeUrl.equals("null")) {
            View.GONE
        } else {
            View.VISIBLE
        }
        exoPlayerView.previousEpisode.visibility = previousEpisodeButtonVisibility
        if (requireActivity().resources.configuration.orientation == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)
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