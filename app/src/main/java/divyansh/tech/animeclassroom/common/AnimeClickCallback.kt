package divyansh.tech.animeclassroom.common

interface AnimeClickCallback {

    fun onAnimeClicked(animeUrl: String)
    fun onEpisodeClicked(episodeUrl: String, imageUrl: String)
    fun onGenreClicked(genreUrl: String)
    fun onMangaClicked(mangaUrl: String)
}