package divyansh.tech.animeclassroom.home.dataModels

import divyansh.tech.animeclassroom.home.utils.HomeTypes
import divyansh.tech.animeclassroom.models.home.AnimeModel

data class HomeMainModel(
    var typeValue: HomeTypes,
    var animeList: List<AnimeModel>? = null
)

