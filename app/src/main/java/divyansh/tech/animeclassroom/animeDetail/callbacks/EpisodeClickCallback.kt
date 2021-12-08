package divyansh.tech.animeclassroom.animeDetail.callbacks

import android.os.Bundle
import divyansh.tech.animeclassroom.animeDetail.AnimeDetailFragmentDirections
import divyansh.tech.animeclassroom.animeDetail.AnimeDetailViewModel
import divyansh.tech.animeclassroom.common.AnimeClickCallback
import divyansh.tech.animeclassroom.player.PlayerViewModel

class EpisodeClickCallback(
    private val viewModel: AnimeDetailViewModel
): AnimeClickCallback {
    override fun onAnimeClicked(animeUrl: String) {
        // do nothing
    }

    override fun onEpisodeClicked(episodeUrl: String, imageUrl: String) {
        val bundle = Bundle().apply {
            putString("url", episodeUrl)
            putInt("type", 0)
            putString("imageUrl", imageUrl)
        }
        viewModel.navigateToPlayerFragment(bundle)
    }

    override fun onGenreClicked(genreUrl: String) {
        val action = AnimeDetailFragmentDirections.actionAnimeDetailFragmentToGenreFragment(genreUrl)
        viewModel.changeNavigation(action)
    }

    override fun onMangaClicked(mangaUrl: String) {
    }

}