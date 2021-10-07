package divyansh.tech.animeclassroom

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AnimeClassroom: Application() {
    override fun onCreate() {
        super.onCreate()
    }
}