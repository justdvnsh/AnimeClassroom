package divyansh.tech.animeclassroom.models.home

data class PlayerScreenModel(
    val animeName: String,
    val streamingUrl: String,
    val mirrorLinks: ArrayList<String>? = null,
    val nextEpisodeUrl: String? = null,
    val previousEpisodeUrl: String? = null
)