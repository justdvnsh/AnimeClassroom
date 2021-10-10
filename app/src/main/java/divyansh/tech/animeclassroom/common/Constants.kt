package divyansh.tech.animeclassroom.common

object Constants {
    internal const val THEME = "ui_mode"

    enum class UI_MODE(val value: Int){
        LIGHT_MODE(0),
        DARK_MODE(1),
        SYSTEM_MODE(2)
    }
}