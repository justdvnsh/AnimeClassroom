package divyansh.tech.animeclassroom.player


import android.app.PictureInPictureParams
import android.content.Intent
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.navArgs
import dagger.hilt.android.AndroidEntryPoint
import divyansh.tech.animeclassroom.R


@AndroidEntryPoint
class PlayerActivity : AppCompatActivity() {

    var episodeUrl:String?=""
    private val viewModel by viewModels<PlayerViewModel>()
    private lateinit var pipParams: PictureInPictureParams
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)
        setupStatusBar()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            pipParams =
                PictureInPictureParams.Builder()
                    .build()
        }

        episodeUrl = intent?.extras?.getString("episodeUrl")
        episodeUrl?.let {

        updateEpisode(it)
        }
    }


    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)


        episodeUrl = intent?.extras?.getString("episodeUrl")
        episodeUrl?.let {

            updateEpisode(it)
        }
    }

    fun updateEpisode(url: String) {
        viewModel.getStreamingUrl(url)
    }

    private fun setupStatusBar() {
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WALLPAPER)
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }

    override fun onBackPressed() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            enterPictureInPictureMode(pipParams)
        }

    }

    override fun onUserLeaveHint() {
        super.onUserLeaveHint()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            enterPictureInPictureMode(pipParams)
        }
    }

}