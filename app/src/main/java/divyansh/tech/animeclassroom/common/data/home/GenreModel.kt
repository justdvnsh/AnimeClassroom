package divyansh.tech.animeclassroom.common.data.home

import com.google.gson.annotations.SerializedName

data class GenreModel(
    var genreTitle: String,
    var genreUrl: String
)

//@RealmClass
//open class OfflineGenreModel(
//    @SerializedName("genreTitle")
//    var genreTitle: String = "",
//    @SerializedName("genreUrl")
//    var genreUrl: String = ""
//): RealmObject()