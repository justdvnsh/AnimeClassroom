package divyansh.tech.animeclassroom.favorites.screens.manga

import divyansh.tech.animeclassroom.common.AnimeClickCallback
import divyansh.tech.animeclassroom.common.CommonViewModel
import divyansh.tech.animeclassroom.favorites.FavoritesFragmentDirections

class FavoriteMangaCallbacks(private val viewModel: CommonViewModel): AnimeClickCallback {
    override fun onMangaClicked(mangaUrl: String) {
        viewModel.changeNavigation(
            FavoritesFragmentDirections.actionFavoritesFragmentToMangaDetailFragment(
                url = mangaUrl
            )
        )
    }

    override fun onAnimeClicked(animeUrl: String) {}
    override fun onEpisodeClicked(episodeUrl: String) {}
    override fun onGenreClicked(genreUrl: String) {}
}