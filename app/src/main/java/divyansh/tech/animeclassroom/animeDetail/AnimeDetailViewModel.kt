package divyansh.tech.animeclassroom.animeDetail

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import divyansh.tech.animeclassroom.Event
import divyansh.tech.animeclassroom.ResultWrapper
import divyansh.tech.animeclassroom.common.CommonViewModel
import divyansh.tech.animeclassroom.di.DispatcherModule
import divyansh.tech.animeclassroom.favorites.FavoriteAnimeDefaultRepo
import divyansh.tech.animeclassroom.models.home.AnimeDetailModel
import divyansh.tech.animeclassroom.models.home.EpisodeModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnimeDetailViewModel @Inject constructor(
    private val repo: AnimeDetailRepo,
    private val favRepo: FavoriteAnimeDefaultRepo,
    @DispatcherModule.IODispatcher private val coroutineDispatcher: CoroutineDispatcher
): CommonViewModel(){

    private val _animeDetailLiveData = MutableLiveData<ResultWrapper<AnimeDetailModel>>()
    val animeDetailLiveData get() = _animeDetailLiveData

    private val _episodeListLiveData: MutableLiveData<ResultWrapper<List<EpisodeModel>>> = MutableLiveData()
    val episodeListLiveData get() = _episodeListLiveData

    private val _animeSavedLiveData: MutableLiveData<Event<Boolean>> = MutableLiveData()
    val animeSavedLiveData get() = _animeSavedLiveData

    fun getAnimeDetails(url: String) = viewModelScope.launch(Dispatchers.IO) {
        _animeDetailLiveData.postValue(ResultWrapper.Loading())
        when(val response = repo.getAnimeDetails(url)) {
            is ResultWrapper.Success -> {
                _animeDetailLiveData.postValue(
                    ResultWrapper.Success(response.data as AnimeDetailModel)
                )
                _episodeListLiveData.postValue(
                    repo.getEpisodeList((response.data as AnimeDetailModel).endEpisode, url)
                )
            }
            else -> {}
        }
    }

    fun saveAnime(anime: AnimeDetailModel, animeUrl: String) {
        viewModelScope.launch(coroutineDispatcher) {
            favRepo.saveAnime(anime, animeUrl)
            _animeSavedLiveData.postValue(Event(true))
            favRepo.getAllAnimes()
        }
    }

}