package divyansh.tech.animeclassroom.animeDetail.callbacks

import divyansh.tech.animeclassroom.animeDetail.AnimeDetailViewModel
import divyansh.tech.animeclassroom.models.home.AnimeDetailModel

class EpisodeItemClickCallback(
    private val viewModel: AnimeDetailViewModel
) {

    fun onEpisodeItemClicked(episodeUrl: String) {
        viewModel.changeEpisodeClickedLiveData(episodeUrl)
    }
}