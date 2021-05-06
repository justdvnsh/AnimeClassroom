package divyansh.tech.animeclassroom.models.home

/*
* Model for Anime's
* */
data class AnimeModel(
    var name: String,
    var imageUrl: String,
    var releaseDate: String,
    var category: String? = null
)