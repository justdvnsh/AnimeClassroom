package divyansh.tech.animeclassroom.models.home

data class PlayerScreenModel(
    val animeName: String,
    val streamingUrl: String,
//    val episodeNumber: String,
    val nextEpisodeUrl: String? = null,
    val previousEpisodeUrl: String? = null
)