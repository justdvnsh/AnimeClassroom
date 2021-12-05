package divyansh.tech.animeclassroom.common.data

data class MangaDetail(
    val name: String,
    val imageUrl: String,
    val genreModel: ArrayList<GenreModel>,
    val summary: String,
    val chapters: ArrayList<Chapters>
)
