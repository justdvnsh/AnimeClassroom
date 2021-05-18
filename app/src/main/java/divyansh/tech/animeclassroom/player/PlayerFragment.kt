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
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
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
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import dagger.hilt.android.AndroidEntryPoint
import divyansh.tech.animeclassroom.R
import divyansh.tech.animeclassroom.ResultWrapper
import divyansh.tech.animeclassroom.databinding.FragmentPlayerBinding
import divyansh.tech.animeclassroom.models.home.PlayerScreenModel
import kotlinx.android.synthetic.main.exo_player_custom_controls.*
import kotlinx.android.synthetic.main.exo_player_custom_controls.view.*
import kotlinx.android.synthetic.main.fragment_player.*

@AndroidEntryPoint
class PlayerFragment: Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentPlayerBinding
    private lateinit var exoPlayer: ExoPlayer
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
    }

    private fun initializePlayer(data: PlayerScreenModel) {
        val trackSelector = DefaultTrackSelector()
        val loadControl = DefaultLoadControl()
        val renderersFactory = DefaultRenderersFactory(requireContext())

        exoPlayer = ExoPlayerFactory.newSimpleInstance(requireContext(), renderersFactory, trackSelector, loadControl)
        binding.exoPlayerView.player = exoPlayer
        if (data.nextEpisodeUrl.equals("null")) binding.exoPlayerView.nextEpisode.visibility = View.GONE
        if (data.previousEpisodeUrl == null) binding.exoPlayerView.previousEpisode.visibility = View.GONE
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

        viewModel.clickControlLiveData.observe(
                viewLifecycleOwner,
                Observer {
                    when (it) {
                        PlayerViewModel.PlayerClick.BACK -> Toast.makeText(requireContext(), "BACK", Toast.LENGTH_SHORT).show()
                        PlayerViewModel.PlayerClick.QUALITY_CONTROL -> Toast.makeText(requireContext(), "QUALITY", Toast.LENGTH_SHORT).show()
                        PlayerViewModel.PlayerClick.SPEED_CONTROL -> Toast.makeText(requireContext(), "SPEED", Toast.LENGTH_SHORT).show()
                        PlayerViewModel.PlayerClick.FULLSCREEN_TOGGLE -> Toast.makeText(requireContext(), "FULLSCREEN", Toast.LENGTH_SHORT).show()
                        PlayerViewModel.PlayerClick.PREV_EPISODE -> Toast.makeText(requireContext(), "PREVIOUS", Toast.LENGTH_SHORT).show()
                        PlayerViewModel.PlayerClick.NEXT_EPISODE -> Toast.makeText(requireContext(), "NEXT", Toast.LENGTH_SHORT).show()
                    }
                }
        )
    }

//    private fun hideSystemUi() {
//        binding.exoPlayerView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LOW_PROFILE
//                or View.SYSTEM_UI_FLAG_FULLSCREEN
//                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
//                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)
//    }

    override fun onResume() {
        super.onResume()
//        hideSystemUi()
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

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.back -> requireActivity().finish()
            R.id.exo_speed_selection_view -> Toast.makeText(requireContext(), "Speed Selection", Toast.LENGTH_SHORT).show()
            R.id.exo_track_selection_view -> Toast.makeText(requireContext(), "Track Selection", Toast.LENGTH_SHORT).show()
            R.id.exo_full_Screen -> toggleFullScreen()
            R.id.previousEpisode -> Toast.makeText(requireContext(), "Previous Episode", Toast.LENGTH_SHORT).show()
            R.id.nextEpisode -> Toast.makeText(requireContext(), "Next Episode", Toast.LENGTH_SHORT).show()
        }
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
                                    R.drawable.exo_controls_fullscreen_enter
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
                                    R.drawable.exo_controls_fullscreen_exit
                            )
                    )
        }
    }
}