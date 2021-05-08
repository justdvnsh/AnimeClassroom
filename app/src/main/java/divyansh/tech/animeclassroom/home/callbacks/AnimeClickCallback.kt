package divyansh.tech.animeclassroom.home.callbacks

import divyansh.tech.animeclassroom.home.HomeViewModel
import divyansh.tech.animeclassroom.models.home.AnimeModel
import divyansh.tech.animeclassroom.models.home.GenreModel

/*
* Collection of click callbacks on the home screen
* */
class AnimeClickCallback(val viewModel: HomeViewModel) {

    /*
    * When user clicks on popular animes, movies or new seasons
    * */
    fun onAnimeClicked(anime: AnimeModel) = viewModel.changeOnAnimeClickedLiveDataValue(anime)

    /*
    * When user clicks on episodes
    * */
    fun onEpisodeClicked(anime: AnimeModel) = viewModel.changeOnEpisodeClickedLiveDataValue(anime)

    /*
    * When user clicks on genres
    * */
    fun onGenreClicked(genreModel: GenreModel) = viewModel.changeOnGenreClickedLiveDataValue(genreModel)
}