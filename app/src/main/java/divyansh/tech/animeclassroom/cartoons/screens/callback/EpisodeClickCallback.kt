package divyansh.tech.animeclassroom.cartoons.screens.callback

import divyansh.tech.animeclassroom.common.data.cartoonModels.Cartoons
import divyansh.tech.animeclassroom.cartoons.screens.CartoonEpisodeFragmentDirections
import divyansh.tech.animeclassroom.cartoons.screens.CartoonEpisodeViewModel

class EpisodeClickCallback(
    private val viewModel: CartoonEpisodeViewModel
) {

    fun onCartoonEpisodeClick(cartoon: Cartoons) {
        viewModel.changeNavigation(
            CartoonEpisodeFragmentDirections.actionGlobalPlayerActivity(cartoon.cartoonUrl, 1)
        )
    }
}