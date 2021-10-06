package divyansh.tech.animeclassroom.player

import android.app.AlertDialog
import android.content.DialogInterface
import android.net.Uri
import android.os.Bundle
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaControllerCompat
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.Player.*
import com.google.android.exoplayer2.ext.mediasession.MediaSessionConnector
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.hls.HlsDataSourceFactory
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import com.google.android.exoplayer2.upstream.HttpDataSource
import dagger.hilt.android.AndroidEntryPoint
import divyansh.tech.animeclassroom.R
import divyansh.tech.animeclassroom.ResultWrapper
import divyansh.tech.animeclassroom.databinding.FragmentPlayerBinding
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
    private lateinit var mediaSessionCompat: MediaSessionCompat
    private var isFullScreen = false
    val MEDIA_ACTIONS_PLAY_PAUSE = (PlaybackStateCompat.ACTION_PLAY
            or PlaybackStateCompat.ACTION_PAUSE
            or PlaybackStateCompat.ACTION_PLAY_PAUSE)
    val MEDIA_ACTIONS_ALL: Long = (MEDIA_ACTIONS_PLAY_PAUSE
            or PlaybackStateCompat.ACTION_SKIP_TO_NEXT
            or PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS)

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

        binding.retry.setOnClickListener {
            if (exoPlayer.playbackError != null)
                exoPlayer.retry()
        }


    }

    private fun initializePlayer(data: PlayerScreenModel) {
        val trackSelector = DefaultTrackSelector()
        val loadControl = DefaultLoadControl()
        val renderersFactory = DefaultRenderersFactory(requireContext())

        exoPlayer = ExoPlayerFactory.newSimpleInstance(
            requireContext(),
            renderersFactory,
            trackSelector,
            loadControl
        )
        mediaSessionCompat = MediaSessionCompat(requireContext(), TAG)
        mediaSessionCompat.isActive = true
        val connector = MediaSessionConnector(mediaSessionCompat)
        MediaControllerCompat.setMediaController(requireActivity(), mediaSessionCompat.controller)
        connector.setPlayer(exoPlayer)
        val metadata = MediaMetadataCompat.Builder()
            .putString(MediaMetadataCompat.METADATA_KEY_DISPLAY_TITLE, data.animeName)
            .build()

        mediaSessionCompat.setMetadata(metadata)



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

                    }
                    STATE_READY -> {
                        exoplayerErrorLayoutChange(visible = false)
                    }
                    STATE_ENDED -> {
                        exoplayerErrorLayoutChange(visible = false)

                    }
                }
            }

        })
        binding.exoPlayerView.player = exoPlayer
        play(data)
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
            {
                when (it) {
                    PlayerViewModel.PlayerClick.BACK ->
                        requireActivity().finish()
                    PlayerViewModel.PlayerClick.QUALITY_CONTROL -> Toast.makeText(
                        requireContext(),
                        "QUALITY",
                        Toast.LENGTH_SHORT
                    ).show()
                    PlayerViewModel.PlayerClick.SPEED_CONTROL -> speedControl()
                    PlayerViewModel.PlayerClick.FULLSCREEN_TOGGLE -> toggleFullScreen()
                }
            }
        )
    }

    override fun onPause() {
        super.onPause()
        if (exoPlayer.isPlaying) {
            if (requireActivity().isInPictureInPictureMode.not())
                releasePlayer()
        }
        else {
            releasePlayer()

        }
    }

    override fun onStop() {
        super.onStop()

        if (requireActivity().isInPictureInPictureMode.not())
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

    private fun speedControl(){
        var playbackPosition = 2
        val alertDialog: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        alertDialog.setTitle("Set your playback speed")
        val items = arrayOf("0.5","0.75","1x", "1.25x", "1.5x", "2x")
        alertDialog.setItems(items, DialogInterface.OnClickListener { _, pos ->
            when (pos) {
                0 -> exoPlayer.playbackParameters = PlaybackParameters(0.5f).also { exo_speed_selection_view.text = "0.5x" }
                1 -> exoPlayer.playbackParameters = PlaybackParameters(0.75f).also { exo_speed_selection_view.text = "0.75x" }
                2 -> exoPlayer.playbackParameters = PlaybackParameters(1f).also { exo_speed_selection_view.text = "1x" }
                3 -> exoPlayer.playbackParameters = PlaybackParameters(1.25f).also { exo_speed_selection_view.text = "1.25x" }
                4 -> exoPlayer.playbackParameters = PlaybackParameters(1.5f).also { exo_speed_selection_view.text = "1.5x" }
                5 -> exoPlayer.playbackParameters = PlaybackParameters(2f).also { exo_speed_selection_view.text = "2x" }
            }
            playbackPosition = pos
        })
        val alert: AlertDialog = alertDialog.create()
        alert.setCanceledOnTouchOutside(true)
        alert.show()
    }

    override fun onButtonClicked(item: PlayerViewModel.PlayerClick) {
        viewModel.updateButtonClick(item)
    }

    override fun onEpisodeClicked(episodeUrl: String) {
        exoPlayer.playWhenReady = false
        (requireActivity() as PlayerActivity).updateEpisode(episodeUrl)
    }

    override fun onPictureInPictureModeChanged(isInPictureInPictureMode: Boolean) {

        if (isInPictureInPictureMode) {
            hideExoplayerContents(false)
            exoPlayer.playWhenReady = true
        } else {
            hideExoplayerContents(true)
            exoPlayer.playWhenReady=false
             }
    }


    private fun hideExoplayerContents(hide: Boolean) {
        binding.exoPlayerView.useController = hide
    }




    companion object {
        private const val TAG = "PlayerFragment"
    }
}