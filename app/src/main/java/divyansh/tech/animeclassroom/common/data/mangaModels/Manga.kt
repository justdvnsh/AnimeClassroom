package divyansh.tech.animeclassroom.common.data.mangaModels

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class Manga(
    var name: String,
    var mangaUrl: String,
    var imageUrl: String,
    var chapterNum: String? = null,
    var chapterUrl: String? = null
)

@Entity(tableName = "manga")
open class OfflineMangaModel(
    @PrimaryKey(autoGenerate = false)
    @SerializedName("imageUrl")
    var imageUrl: String,
    @SerializedName("name")
    var name: String,
    @SerializedName("mangaUrl")
    var mangaUrl: String,
    @SerializedName("chapterNum")
    var chapterNum: String? = null,
    @SerializedName("chapterUrl")
    var chapterUrl: String? = null
)


