package divyansh.tech.animeclassroom.common

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import divyansh.tech.animeclassroom.models.home.AnimeModel
import divyansh.tech.animeclassroom.models.home.GenreModel

open class CommonViewModel: ViewModel() {

    // Clicked events
    private val _onAnimeClickedEventLiveData: MutableLiveData<AnimeModel> = MutableLiveData()
    val onAnimeClickedEventLiveData: LiveData<AnimeModel> get() = _onAnimeClickedEventLiveData

    private val _onEpisodeClickedLiveData: MutableLiveData<AnimeModel> = MutableLiveData()
    val onEpisodeClickedEventLiveData: LiveData<AnimeModel> get() = _onEpisodeClickedLiveData

    private val _onGenreClickedEventLiveData: MutableLiveData<GenreModel> = MutableLiveData()
    val onGenreClickedEventLiveData: LiveData<GenreModel> get() = _onGenreClickedEventLiveData

    /*
   * Helper functions -> Events
   * */
    fun changeOnAnimeClickedLiveDataValue(anime: AnimeModel) {
        _onAnimeClickedEventLiveData.value = anime
    }

    fun changeOnEpisodeClickedLiveDataValue(anime: AnimeModel) {
        _onEpisodeClickedLiveData.value = anime
    }

    fun changeOnGenreClickedLiveDataValue(genre: GenreModel) {
        _onGenreClickedEventLiveData.value = genre
    }
}