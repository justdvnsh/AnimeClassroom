package divyansh.tech.animeclassroom.player

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.navigation.navArgs
import dagger.hilt.android.AndroidEntryPoint
import divyansh.tech.animeclassroom.R

@AndroidEntryPoint
class PlayerActivity : AppCompatActivity() {

    val args: PlayerActivityArgs by navArgs()
    private val viewModel by viewModels<PlayerViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)
        setupStatusBar()
    }

    override fun onResume() {
        super.onResume()
        updateEpisode(args.episodeUrl, args.type)
    }

    fun updateEpisode(url: String, type: Int) {
        if (type == 0) viewModel.getStreamingUrl(url)
        if (type == 1) viewModel.getCartoonStreamingUrl(url)
    }

    private fun setupStatusBar() {
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WALLPAPER)
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }
}