package divyansh.tech.animeclassroom.animeDetail.callbacks

import divyansh.tech.animeclassroom.animeDetail.AnimeDetailViewModel
import divyansh.tech.animeclassroom.common.data.AnimeDetailModel

class FavoriteClickCallback(
    private val viewModel: AnimeDetailViewModel
) {

    fun onFavoriteClicked(anime: AnimeDetailModel, animeUrl: String) {
        viewModel.saveAnime(anime, animeUrl)
    }
}