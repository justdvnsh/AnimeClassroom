package divyansh.tech.animeclassroom.models.home

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/*
* Model for Anime's
* */
data class AnimeModel(
        var name: String,
        var imageUrl: String?,
        var releaseDate: String? = null,
        var animeUrl: String? = null,
        var episodeUrl: String? = null,
        var episodeNumber: String? = null,
        var genre: String? = null,
)

data class AnimeDetailModel(
    var imageUrl: String,
    var name: String,
    var releaseDate: String,
    var endEpisode: Int,
    var genre: List<GenreModel>,
    var plotSummary: String,
    var status: String,
    var type: String,
    var isSaved: Boolean = false
)

@Entity(tableName = "animes")
open class OfflineAnimeModel(
    @PrimaryKey(autoGenerate = false)
    @SerializedName("imageUrl")
    var imageUrl: String = "",
    @SerializedName("name")
    var name: String = "",
    @SerializedName("animeUrl")
    var animeUrl: String? = null,
    @SerializedName("category")
    var category: String? = null,
    @SerializedName("releaseDate")
    var releaseDate: String? = null,
    @SerializedName("episodeUrl")
    var episodeUrl: String? = null,
    @SerializedName("episodeNumber")
    var episodeNumber: String? = null,
    @SerializedName("genre")
    var genre: String? = null
)