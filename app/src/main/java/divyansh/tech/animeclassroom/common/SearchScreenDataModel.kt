package divyansh.tech.animeclassroom.common

import divyansh.tech.animeclassroom.common.data.mangaModels.Manga
import divyansh.tech.animeclassroom.common.data.home.AnimeModel

data class SearchScreenDataModel(
    val mangas: ArrayList<Manga>,
    val animes: ArrayList<AnimeModel>
)