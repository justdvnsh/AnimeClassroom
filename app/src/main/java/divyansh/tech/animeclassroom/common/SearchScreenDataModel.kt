package divyansh.tech.animeclassroom.common

import divyansh.tech.animeclassroom.mangaModels.Manga
import divyansh.tech.animeclassroom.models.home.AnimeModel

data class SearchScreenDataModel(
    val mangas: ArrayList<Manga>,
    val animes: ArrayList<AnimeModel>
)