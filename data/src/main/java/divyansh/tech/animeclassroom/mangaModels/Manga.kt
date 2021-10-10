package divyansh.tech.animeclassroom.mangaModels

data class Manga(
    var name: String,
    var mangaUrl: String? = null,
    var imageUrl: String,
    var chapterNum: String? = null,
    var chapterUrl: String? = null
)
