package divyansh.tech.animeclassroom.favorites.screens.animes

import divyansh.tech.animeclassroom.common.AnimeClickCallback
import divyansh.tech.animeclassroom.common.CommonViewModel
import divyansh.tech.animeclassroom.favorites.FavoritesFragmentDirections

class FavoriteAnimeCallbacks(private val viewModel: CommonViewModel) : AnimeClickCallback {
    override fun onAnimeClicked(animeUrl: String) {
        viewModel.changeNavigation(
            FavoritesFragmentDirections.actionFavoritesFragmentToAnimeDetailFragment(
                animeUrl = animeUrl
            )
        )
    }

    override fun onEpisodeClicked(episodeUrl: String) {}

    override fun onGenreClicked(genreUrl: String) {}
    override fun onMangaClicked(mangaUrl: String) {}
}