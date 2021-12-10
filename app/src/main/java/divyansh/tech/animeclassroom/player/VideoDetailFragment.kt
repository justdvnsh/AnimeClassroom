package divyansh.tech.animeclassroom.player

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.exoplayer2.PlaybackParameters
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import dagger.hilt.android.AndroidEntryPoint
import divyansh.tech.animeclassroom.R
import divyansh.tech.animeclassroom.common.AnimeClickCallback
import divyansh.tech.animeclassroom.common.utils.EventObserver
import divyansh.tech.animeclassroom.common.utils.ResultWrapper
import divyansh.tech.animeclassroom.databinding.FragmentVideoDetailBinding
import divyansh.tech.animeclassroom.player.epoxy.EpoxyPlayerController
import kotlinx.android.synthetic.main.exo_player_custom_controls.*
import kotlinx.android.synthetic.main.video_player_fragment.*

@AndroidEntryPoint
class VideoDetailFragment: Fragment() {

    private lateinit var binding: FragmentVideoDetailBinding

    private val args by navArgs<VideoDetailFragmentArgs>()

    private val viewModel by activityViewModels<PlayerViewModel>()
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
    ): View? {
        binding = FragmentVideoDetailBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        setupRecyclerView()
        setupObservers()
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

    private fun setupObservers() {
        viewModel.streamingLiveData.observe(
            viewLifecycleOwner,
            Observer {
                when (it) {
                    is ResultWrapper.Success -> {
                        Log.i("Player-Frag", it.data.toString())
                        it.data?.let { it1 ->
                            childFragmentManager.beginTransaction()
                                .replace(R.id.videoPlayerFragment, PlayerFragment())
                                .commit()
                            controller.setData(it1)
                        }
                    }
                    is ResultWrapper.Loading -> {
//                        setEnabledStatusOfPreviousAndNextEpisodesButtons(false)
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

    private fun setupViews() {
        requireView().setOnKeyListener { _, code, keyEvent ->
            if (code == KeyEvent.KEYCODE_BACK)
                onBackPressed()
            true
        }
    }

    fun onBackPressed() {
        if (requireActivity().requestedOrientation == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)
            requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        else findNavController().popBackStack()
    }

    override fun onResume() {
        super.onResume()
        viewModel.updateEpisode(args.url, args.type)
    }

    private fun speedControl(){
        var playbackPosition = 2
        val alertDialog: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        alertDialog.setTitle("Set your playback speed")
        val items = arrayOf("0.5","0.75","1x", "1.25x", "1.5x", "2x")
        alertDialog.setItems(items, DialogInterface.OnClickListener { _, pos ->
            when (pos) {
                0 -> viewModel.updateSpeedControl(PlaybackParameters(0.5f))
                1 -> viewModel.updateSpeedControl(PlaybackParameters(0.75f))
                2 -> viewModel.updateSpeedControl(PlaybackParameters(1f))
                3 -> viewModel.updateSpeedControl(PlaybackParameters(1.25f))
                4 -> viewModel.updateSpeedControl(PlaybackParameters(1.5f))
                5 -> viewModel.updateSpeedControl(PlaybackParameters(2f))
            }
            playbackPosition = pos
        })
        val alert: AlertDialog = alertDialog.create()
        alert.setCanceledOnTouchOutside(true)
        alert.show()
    }

    /*
    * Method Selections
    * */
    private fun toggleFullScreen() {
        if (requireActivity().requestedOrientation == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            exoPlayerFrameLayout.resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FILL
            exoPlayerView.resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FILL
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
}