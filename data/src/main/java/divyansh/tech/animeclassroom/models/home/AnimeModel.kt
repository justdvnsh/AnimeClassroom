package divyansh.tech.animeclassroom.models.home

/*
* Model for Anime's
* */
data class AnimeModel(
    var name: String,
    var imageUrl: String,
    var releaseDate: String,
    var animeUrl: String,
    var genre: String? = null
)

/*
* Model for animeMeta
* */
data class AnimeMetaModel(
    var name: String,
    var imageUrl: String,
    var episodeUrl: String,
    var episodeNumber: String
)