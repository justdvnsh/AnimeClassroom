package divyansh.tech.animeclassroom.animeDetail.callbacks

import divyansh.tech.animeclassroom.animeDetail.AnimeDetailFragmentDirections
import divyansh.tech.animeclassroom.animeDetail.AnimeDetailViewModel
import divyansh.tech.animeclassroom.common.AnimeClickCallback

class EpisodeClickCallback(
    private val viewModel: AnimeDetailViewModel
): AnimeClickCallback {
    override fun onAnimeClicked(animeUrl: String) {
        // do nothing
    }

    override fun onEpisodeClicked(episodeUrl: String) {
        val action = AnimeDetailFragmentDirections.actionGlobalPlayerActivity(episodeUrl)
        viewModel.changeNavigation(action)
    }

    override fun onGenreClicked(genreUrl: String) {
        val action = AnimeDetailFragmentDirections.actionAnimeDetailFragmentToGenreFragment(genreUrl)
        viewModel.changeNavigation(action)
    }

    override fun onMangaClicked(mangaUrl: String) {
    }

}