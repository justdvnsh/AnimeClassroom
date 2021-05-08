package divyansh.tech.animeclassroom.home.dataModels

import divyansh.tech.animeclassroom.home.utils.HomeTypes
import divyansh.tech.animeclassroom.models.home.AnimeModel
import divyansh.tech.animeclassroom.models.home.GenreModel

data class HomeMainModel(
    var type: HomeTypes,
    val feedResult: ArrayList<*>
)

//data class AnimeScreenModel(
//    var typeValue: HomeTypes,
//    var animeList: ArrayList<AnimeModel>? = null
//)
//
//data class GenreScreenModels(
//    var typeValue: HomeTypes,
//    var genreList: ArrayList<GenreModel>
//)

