package divyansh.tech.animeclassroom

/*
* Constants object
* */
object C {

    const val BASE_URL = "https://gogoanime.pe/"
    const val MANGA_URL = "https://mangadex.tv/"

    const val THEME = "ui_mode"
    enum class UI_MODE(val value: Int){
        LIGHT_MODE(0),
        DARK_MODE(1),
        SYSTEM_MODE(2)
    }
}