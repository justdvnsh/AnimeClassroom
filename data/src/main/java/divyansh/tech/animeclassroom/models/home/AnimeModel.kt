package divyansh.tech.animeclassroom.models.home

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

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
): Serializable


data class AnimeDetailModel(
    var name: String,
    var imageUrl: String,
    var releaseDate: String,
    var episodeList: List<EpisodeModel>,
    var genre: List<GenreModel>,
    var plotSummary: String,
    var status: String,
    var type: String
)