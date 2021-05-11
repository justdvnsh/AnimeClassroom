package divyansh.tech.animeclassroom.animeDetail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import divyansh.tech.animeclassroom.ResultWrapper
import divyansh.tech.animeclassroom.models.home.AnimeDetailModel
import divyansh.tech.animeclassroom.models.home.AnimeModel
import divyansh.tech.animeclassroom.models.home.EpisodeModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnimeDetailViewModel @Inject constructor(
    private val animeDetailRepo: AnimeDetailRepo
): ViewModel(){

    private val _animeDetailLiveData: MutableLiveData<ResultWrapper<AnimeDetailModel>> = MutableLiveData()
    val animeDetailLiveData: LiveData<ResultWrapper<AnimeDetailModel>> get() = _animeDetailLiveData

    private val _episodeListLiveData: MutableLiveData<ResultWrapper<List<EpisodeModel>>> = MutableLiveData()
    val episodeListLiveData: LiveData<ResultWrapper<List<EpisodeModel>>> get() = _episodeListLiveData

    private val _episodeClickedLiveData: MutableLiveData<String?> = MutableLiveData()
    val episodeClickedLiveData: LiveData<String?> get() = _episodeClickedLiveData

    fun getAnimeDetails(animeUrl: String) = viewModelScope.launch(Dispatchers.IO) {
        Log.i("HOME-VIEWMODEL", animeUrl)
        _animeDetailLiveData.postValue(ResultWrapper.Loading())
        when (val response = animeDetailRepo.getAnimeDetails(animeUrl)) {
            is ResultWrapper.Success -> {
                _animeDetailLiveData.postValue(ResultWrapper.Success(response.data as AnimeDetailModel))
                _episodeListLiveData.postValue(animeDetailRepo.getEpisodeList((response.data as AnimeDetailModel).endEpisode, animeUrl))
            }
            is ResultWrapper.Error -> _animeDetailLiveData.postValue(ResultWrapper.Error(response.message.toString()))
        }
    }

    fun changeEpisodeClickedLiveData(episodeUrl: String) {
        _episodeClickedLiveData.value = episodeUrl
    }
}