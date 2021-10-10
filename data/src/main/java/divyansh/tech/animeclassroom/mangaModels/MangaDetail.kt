package divyansh.tech.animeclassroom.mangaModels

import divyansh.tech.animeclassroom.models.home.GenreModel

data class MangaDetail(
    val name: String,
    val imageUrl: String,
    val genreModel: ArrayList<GenreModel>,
    val summary: String,
    val chapters: ArrayList<Chapters>
)
