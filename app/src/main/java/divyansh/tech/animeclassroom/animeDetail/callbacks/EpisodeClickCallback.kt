package divyansh.tech.animeclassroom.animeDetail.callbacks

import android.util.Log
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
        viewModel.startPlayerWithNewIntent(episodeUrl)
    }

    override fun onGenreClicked(genreUrl: String) {
        // do nothing
    }
companion object{
    private const val TAG = "EpisodeClickCallback"
}
}