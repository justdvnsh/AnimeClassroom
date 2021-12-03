package divyansh.tech.animeclassroom.home.callbacks

import divyansh.tech.animeclassroom.common.AnimeClickCallback
import divyansh.tech.animeclassroom.common.CommonViewModel
import divyansh.tech.animeclassroom.searchAnime.SearchAnimeFragmentDirections

/*
* Collection of click callbacks on the home screen
* */
class SearchScreenCallbacks(private val viewModel: CommonViewModel): AnimeClickCallback {

    /*
    * When user clicks on popular animes, movies or new seasons
    * */

    override fun onAnimeClicked(animeUrl: String) {
        val action = SearchAnimeFragmentDirections.actionSearchFragmentToAnimeDetailFragment(animeUrl)
        viewModel.changeNavigation(action)
    }

    override fun onEpisodeClicked(episodeUrl: String) {

    }

    override fun onGenreClicked(genreUrl: String) {
    }

    override fun onMangaClicked(mangaUrl: String) {
        viewModel.changeNavigation(
            SearchAnimeFragmentDirections.actionSearchFragmentToMangaDetailFragment(mangaUrl)
        )
    }

}