package divyansh.tech.animeclassroom.manga.callbacks

import divyansh.tech.animeclassroom.common.AnimeClickCallback
import divyansh.tech.animeclassroom.manga.MangaFragmentDirections
import divyansh.tech.animeclassroom.manga.MangaViewModel

class MangaClickCallback(
    private val viewModel: MangaViewModel
): AnimeClickCallback {
    override fun onAnimeClicked(animeUrl: String) {
        TODO("Not yet implemented")
    }

    override fun onEpisodeClicked(episodeUrl: String) {
        TODO("Not yet implemented")
    }

    override fun onGenreClicked(genreUrl: String) {
        TODO("Not yet implemented")
    }

    override fun onMangaClicked(mangaUrl: String) {
        val action = MangaFragmentDirections.actionMangaFragmentToMangaDetailFragment(mangaUrl)
        viewModel.changeNavigation(action)
    }
}