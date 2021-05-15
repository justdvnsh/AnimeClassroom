package divyansh.tech.animeclassroom.animeDetail.callbacks

import divyansh.tech.animeclassroom.animeDetail.AnimeDetailFragmentDirections
import divyansh.tech.animeclassroom.animeDetail.AnimeDetailViewModel
import divyansh.tech.animeclassroom.common.AnimeClickCallback
import divyansh.tech.animeclassroom.models.home.AnimeDetailModel

class EpisodeItemClickCallback(
    private val viewModel: AnimeDetailViewModel
): AnimeClickCallback {
    override fun onAnimeClicked(animeUrl: String) {

    }

    override fun onEpisodeClicked(episodeUrl: String) {
        val action = AnimeDetailFragmentDirections.actionAnimeDetailFragmentToPlayerActivity(episodeUrl)
        viewModel.changeNavigation(action)
    }

    override fun onGenreClicked(genreUrl: String) {

    }

}