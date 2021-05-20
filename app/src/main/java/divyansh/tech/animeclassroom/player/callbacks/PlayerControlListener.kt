package divyansh.tech.animeclassroom.player.callbacks

import divyansh.tech.animeclassroom.home.HomeFragmentDirections
import divyansh.tech.animeclassroom.player.PlayerViewModel

interface PlayerControlListener {

    fun onButtonClicked(item: PlayerViewModel.PlayerClick)

    fun onEpisodeClicked(episodeUrl: String)
}