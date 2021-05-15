package divyansh.tech.animeclassroom.common

interface AnimeClickCallback {

    fun onAnimeClicked(animeUrl: String)
    fun onEpisodeClicked(episodeUrl: String)
    fun onGenreClicked(genreUrl: String)
}