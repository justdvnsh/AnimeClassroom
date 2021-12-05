package divyansh.tech.animeclassroom.player

import android.app.Activity
import android.app.AlertDialog
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
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import com.google.android.exoplayer2.upstream.HttpDataSource
import dagger.hilt.android.AndroidEntryPoint
import divyansh.tech.animeclassroom.R
import divyansh.tech.animeclassroom.animeDetail.callbacks.EpisodeClickCallback
import divyansh.tech.animeclassroom.common.AnimeClickCallback
import divyansh.tech.animeclassroom.common.utils.ResultWrapper
import divyansh.tech.animeclassroom.databinding.FragmentPlayerBinding
import divyansh.tech.animeclassroom.common.data.PlayerScreenModel
import divyansh.tech.animeclassroom.common.utils.Event
import divyansh.tech.animeclassroom.common.utils.EventObserver
import divyansh.tech.animeclassroom.player.callbacks.PlayerControlListener
import divyansh.tech.animeclassroom.player.epoxy.EpoxyPlayerController
import kotlinx.android.synthetic.main.exo_player_custom_controls.*
import kotlinx.android.synthetic.main.exo_player_custom_controls.view.*
import kotlinx.android.synthetic.main.fragment_player.*

@AndroidEntryPoint
class PlayerFragment: Fragment(), PlayerControlListener {

    private lateinit var binding: FragmentPlayerBinding
    private lateinit var exoPlayer: SimpleExoPlayer
    private val viewModel by activityViewModels<PlayerViewModel>()

    private val args by navArgs<PlayerFragmentArgs>()

    private var isFullScreen = false

    val callback = object: AnimeClickCallback {
        override fun onAnimeClicked(animeUrl: String) {

        }

        override fun onEpisodeClicked(episodeUrl: String, imageUrl: String) {
            viewModel.updateEpisode(episodeUrl, args.type)
        }

        override fun onGenreClicked(genreUrl: String) {

        }

        override fun onMangaClicked(mangaUrl: String) {

        }

    }

    private val controller by lazy {
        EpoxyPlayerController(
            callback = callback,
            imageUrl = args.imageUrl
        )
    }

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
        setupRecyclerView()
        setupObservers()
        setupListeners()
    }

    private fun setupRecyclerView() {
        val orientation = requireActivity().resources.configuration.orientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        Log.i("PlayerOrientation -> ", orientation.toString())
        if (orientation) {
            binding.episodeRv!!.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = controller.adapter
            }
        }
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
                        binding.exoPlayerView.exo_play.setImageResource(0)
                        binding.exoPlayerView.exo_pause.setImageResource(0)
                    }
                    STATE_READY -> {
                        setEnabledStatusOfPreviousAndNextEpisodesButtons(true)
                        exoPlayerView.videoSurfaceView.visibility = View.VISIBLE
                        exoplayerErrorLayoutChange(visible = false)
                        binding.exoPlayerView.exo_play.setImageResource(R.drawable.ic_media_play)
                        binding.exoPlayerView.exo_pause.setImageResource(R.drawable.ic_media_pause)
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
        if (data.streamingUrl.contains("m3u8")) exoPlayer.prepare(buildMediaSource(data.streamingUrl.toUri()))
        else {
            if (data.streamingUrl.contains("https"))
                exoPlayer.prepare(buildMediaSource(data.streamingUrl.toUri()))
            else
                exoPlayer.prepare(buildMediaSource(data.streamingUrl.replace("http", "https").toUri()))

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
                Observer {
                    when (it) {
                        is ResultWrapper.Success -> {
                            Log.i("Player-Frag", it.data.toString())
                            it.data?.let { it1 ->
                                initializePlayer(it1)
                                binding.progressBar?.visibility = View.GONE
                                binding.videoPlayerContainer.visibility = View.VISIBLE
                                setupViews(it1)
                                controller.setData(it1)
                            }
                        }
                        is ResultWrapper.Loading -> {
                            setEnabledStatusOfPreviousAndNextEpisodesButtons(false)
                            binding.progressBar?.visibility = View.VISIBLE
                            binding.videoPlayerContainer.visibility = View.GONE
                        }
                        else -> {
                            Log.i("Player-Frag", it.toString())
                        }
                    }
                }
        )

        viewModel.clickControlLiveData.observe(
                viewLifecycleOwner,
                EventObserver {
                    when(it) {
                        PlayerViewModel.PlayerClick.BACK -> onBackPressed()
                        PlayerViewModel.PlayerClick.QUALITY_CONTROL -> Toast.makeText(requireContext(), "QUALITY", Toast.LENGTH_SHORT).show()
                        PlayerViewModel.PlayerClick.SPEED_CONTROL -> speedControl()
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
        if (requireActivity().requestedOrientation == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            exoPlayerFrameLayout.resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FILL
            exoPlayerView.resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FILL
            isFullScreen = false
            requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            exo_full_Screen
                    .setImageDrawable(
                            ContextCompat.getDrawable(
                                    requireContext(),
                                    R.drawable.exo_controls_fullscreen_exit
                            )
                    )
        } else {
            exoPlayerFrameLayout.resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FILL
            exoPlayerView.resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FILL
            isFullScreen = true
            requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            exo_full_Screen
                    .setImageDrawable(
                            ContextCompat.getDrawable(
                                    requireContext(),
                                    R.drawable.exo_controls_fullscreen_enter
                            )
                    )
        }
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
        (requireActivity() as PlayerActivity).updateEpisode(episodeUrl, 0)
    }

    override fun onResume() {
        super.onResume()
        viewModel.updateEpisode(args.url, args.type)
    }

    private fun setupViews(it1: PlayerScreenModel) {
        val nextEpisodeButtonVisibility = if (it1.nextEpisodeUrl == null || it1.nextEpisodeUrl.equals("null")) {
            View.GONE
        } else {
            View.VISIBLE
        }
        binding.exoPlayerView.nextEpisode.visibility = nextEpisodeButtonVisibility
        val previousEpisodeButtonVisibility = if (it1.previousEpisodeUrl == null || it1.previousEpisodeUrl.equals("null")) {
            View.GONE
        } else {
            View.VISIBLE
        }
        binding.exoPlayerView.previousEpisode.visibility = previousEpisodeButtonVisibility
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

        requireView().setOnKeyListener { _, code, keyEvent ->
            if (code == KeyEvent.KEYCODE_BACK)
                onBackPressed()
            true
        }
    }

    private fun onBackPressed() {
        if (requireActivity().requestedOrientation == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)
            requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        else findNavController().popBackStack()
    }
}