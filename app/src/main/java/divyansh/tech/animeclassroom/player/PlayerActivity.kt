package divyansh.tech.animeclassroom.player

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import androidx.navigation.navArgs
import com.google.android.exoplayer2.Player
import dagger.hilt.android.AndroidEntryPoint
import divyansh.tech.animeclassroom.R
import divyansh.tech.animeclassroom.animeDetail.AnimeDetailFragmentArgs

@AndroidEntryPoint
class PlayerActivity : AppCompatActivity() {

    val episodeUrl: PlayerActivityArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)
    }
}