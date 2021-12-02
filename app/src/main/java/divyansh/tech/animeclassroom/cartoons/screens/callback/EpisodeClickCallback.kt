package divyansh.tech.animeclassroom.cartoons.screens.callback

import divyansh.tech.animeclassroom.cartoonModels.Cartoons
import divyansh.tech.animeclassroom.cartoons.screens.CartoonEpisodeFragmentDirections
import divyansh.tech.animeclassroom.cartoons.screens.CartoonEpisodeViewModel
import divyansh.tech.animeclassroom.player.PlayerViewModel

class EpisodeClickCallback(
    private val viewModel: CartoonEpisodeViewModel
) {

    fun onCartoonEpisodeClick(cartoon: Cartoons) {
        viewModel.changeNavigation(
            CartoonEpisodeFragmentDirections.actionGlobalPlayerActivity(cartoon.cartoonUrl, 1)
        )
    }
}