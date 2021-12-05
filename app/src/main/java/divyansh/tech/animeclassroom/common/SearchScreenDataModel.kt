package divyansh.tech.animeclassroom.common

import divyansh.tech.animeclassroom.common.data.Manga
import divyansh.tech.animeclassroom.common.data.AnimeModel

data class SearchScreenDataModel(
    val mangas: ArrayList<Manga>,
    val animes: ArrayList<AnimeModel>
)