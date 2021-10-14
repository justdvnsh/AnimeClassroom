package divyansh.tech.animeclassroom.genres.callbacks

import divyansh.tech.animeclassroom.common.AnimeClickCallback
import divyansh.tech.animeclassroom.genres.GenreFragmentDirections
import divyansh.tech.animeclassroom.genres.GenreViewModel

class GenreClickCallbacks(
    private val viewModel: GenreViewModel
): AnimeClickCallback {
    override fun onAnimeClicked(animeUrl: String) {
        viewModel.changeNavigation(
            GenreFragmentDirections.actionGenreFragmentToAnimeDetailFragment(animeUrl)
        )
    }

    override fun onEpisodeClicked(episodeUrl: String) {
        TODO("Not yet implemented")
    }

    override fun onGenreClicked(genreUrl: String) {
        TODO("Not yet implemented")
    }

    override fun onMangaClicked(mangaUrl: String) {
        viewModel.changeNavigation(
            GenreFragmentDirections.actionGenreFragmentToMangaDetailFragment(mangaUrl)
        )
    }

}