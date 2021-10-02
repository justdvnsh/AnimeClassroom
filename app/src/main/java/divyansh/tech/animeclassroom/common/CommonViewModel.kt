package divyansh.tech.animeclassroom.common

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
import divyansh.tech.animeclassroom.Event
import divyansh.tech.animeclassroom.models.home.AnimeModel
import divyansh.tech.animeclassroom.models.home.GenreModel

open class CommonViewModel: ViewModel() {

    // Clicked events
    private val _navigationLiveData: MutableLiveData<Event<NavDirections>> = MutableLiveData()
    val navigation: LiveData<Event<NavDirections>> get() = _navigationLiveData

    /*
   * Helper functions -> Events
   * */
    fun changeNavigation(navDirections: NavDirections) {
        _navigationLiveData.value = Event(navDirections)
    }
}