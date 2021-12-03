package divyansh.tech.animeclassroom.cartoons.callbacks

import divyansh.tech.animeclassroom.common.data.cartoonModels.Cartoons
import divyansh.tech.animeclassroom.cartoons.CartoonFragmentDirections
import divyansh.tech.animeclassroom.cartoons.CartoonViewModel

class CartoonClickCallback(
    private val viewModel: CartoonViewModel
) {

    fun onCartoonClick(cartoon: Cartoons) {
        viewModel.changeNavigation(
            CartoonFragmentDirections.actionCartoonFragmentToCartoonEpisodeFragment(
                name = cartoon.name,
                cartoonUrl = cartoon.cartoonUrl
            )
        )
    }
}