package divyansh.tech.animeclassroom.common.data

data class PlayerScreenModel(
    val animeName: String,
    val streamingUrl: String,
    val mirrorLinks: ArrayList<String>? = null,
    val nextEpisodeUrl: String? = null,
    val previousEpisodeUrl: String? = null,
    val activeEpisode: Int = 0,
    val episodeList: ArrayList<EpisodeModel>  = ArrayList()
)