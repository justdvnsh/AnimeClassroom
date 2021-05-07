package divyansh.tech.animeclassroom.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import divyansh.tech.animeclassroom.ResultWrapper
import divyansh.tech.animeclassroom.models.home.AnimeMetaModel
import divyansh.tech.animeclassroom.models.home.AnimeModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repo: HomeDefaultRepo
): ViewModel() {

    private val _popularAnimeLiveData: MutableLiveData<ResultWrapper<List<AnimeModel>>> = MutableLiveData()
    val popularAnimeLiveData: LiveData<ResultWrapper<List<AnimeModel>>> get() = _popularAnimeLiveData

    private val _recentReleasesLiveData: MutableLiveData<ResultWrapper<List<AnimeMetaModel>>> = MutableLiveData()
    val recentReleaseLiveData: LiveData<ResultWrapper<List<AnimeMetaModel>>> get() = _recentReleasesLiveData

    private val _popularMoviesLiveData: MutableLiveData<ResultWrapper<List<AnimeModel>>> = MutableLiveData()
    val popularMoviesLiveData: LiveData<ResultWrapper<List<AnimeModel>>> get() = _popularMoviesLiveData

    private val _newSeasonsLiveData: MutableLiveData<ResultWrapper<List<AnimeModel>>> = MutableLiveData()
    val newSeasonsLiveData: LiveData<ResultWrapper<List<AnimeModel>>> get() = _newSeasonsLiveData

    init {
        getPopularAnimes()
        getRecentReleases()
        getPopularMovies()
        getNewSeasons()
    }

    private fun getPopularAnimes() = viewModelScope.launch(Dispatchers.IO) {
        _popularAnimeLiveData.postValue(ResultWrapper.Loading())
        val response = repo.parsePopularAnimes()
        response.collect {
            if (it is ResultWrapper.Success)
                _popularAnimeLiveData.postValue(ResultWrapper.Success(it.data as List<AnimeModel>))
            else
                _popularAnimeLiveData.postValue(ResultWrapper.Error(it.message.toString()))
        }
    }

    private fun getRecentReleases() = viewModelScope.launch(Dispatchers.IO) {
        _recentReleasesLiveData.postValue(ResultWrapper.Loading())
        val response = repo.parseRecentReleases()
        response.collect {
            if (it is ResultWrapper.Success)
                _recentReleasesLiveData.postValue(ResultWrapper.Success(it.data as List<AnimeMetaModel>))
            else
                _recentReleasesLiveData.postValue(ResultWrapper.Error(it.message.toString()))
        }
    }

    private fun getPopularMovies() = viewModelScope.launch(Dispatchers.IO) {
        _popularMoviesLiveData.postValue(ResultWrapper.Loading())
        val response = repo.parsePopularMovies()
        response.collect {
            if (it is ResultWrapper.Success)
                _popularMoviesLiveData.postValue(ResultWrapper.Success(it.data as List<AnimeModel>))
            else
                _popularMoviesLiveData.postValue(ResultWrapper.Error(it.message.toString()))
        }
    }

    private fun getNewSeasons() = viewModelScope.launch(Dispatchers.IO) {
        _newSeasonsLiveData.postValue(ResultWrapper.Loading())
        val response = repo.parseNewSeasons()
        response.collect {
            if (it is ResultWrapper.Success)
                _newSeasonsLiveData.postValue(ResultWrapper.Success(it.data as List<AnimeModel>))
            else
                _newSeasonsLiveData.postValue(ResultWrapper.Error(it.message.toString()))
        }
    }
}