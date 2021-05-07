package divyansh.tech.animeclassroom.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import divyansh.tech.animeclassroom.ResultWrapper
import divyansh.tech.animeclassroom.home.dataModels.*
import divyansh.tech.animeclassroom.home.utils.HomeTypes
import divyansh.tech.animeclassroom.models.home.AnimeModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repo: HomeDefaultRepo
): ViewModel() {

    private val _animeList: MutableLiveData<ResultWrapper<List<HomeMainModel>>> = MutableLiveData()
    val animeList: LiveData<ResultWrapper<List<HomeMainModel>>> get() = _animeList

    private val _list: MutableList<HomeMainModel> = mutableListOf()

    init {
        getRecentReleases()
        getPopularAnimes()
        getNewSeasons()
        getPopularMovies()
    }

    private fun getPopularAnimes() = viewModelScope.launch(Dispatchers.IO) {
        _animeList.postValue(ResultWrapper.Loading())
        val response = repo.parsePopularAnimes()
        response.collect {
            if (it is ResultWrapper.Success) {
                val model = HomeMainModel(
                    typeValue = HomeTypes.POPULAR_ANIME,
                    animeList = it.data as List<AnimeModel>)
                _list.add(model)
                _animeList.postValue(ResultWrapper.Success(_list))
            }
            else
                _animeList.postValue(ResultWrapper.Error(it.message.toString()))
        }
    }

    private fun getRecentReleases() = viewModelScope.launch(Dispatchers.IO) {
        _animeList.postValue(ResultWrapper.Loading())
        val response = repo.parseRecentReleases()
        response.collect {
            if (it is ResultWrapper.Success){
                val model = HomeMainModel(
                    typeValue = HomeTypes.RECENT_RELEASE,
                    animeList = it.data as List<AnimeModel>)
                _list.add(model)
                _animeList.postValue(ResultWrapper.Success(_list))
            }
            else
                _animeList.postValue(ResultWrapper.Error(it.message.toString()))
        }
    }

    private fun getPopularMovies() = viewModelScope.launch(Dispatchers.IO) {
        _animeList.postValue(ResultWrapper.Loading())
        val response = repo.parsePopularMovies()
        response.collect {
            if (it is ResultWrapper.Success){
                val model = HomeMainModel(
                    typeValue = HomeTypes.POPULAR_MOVIES,
                    animeList = it.data as List<AnimeModel>)
                _list.add(model)
                _animeList.postValue(ResultWrapper.Success(_list))
            }
            else
                _animeList.postValue(ResultWrapper.Error(it.message.toString()))
        }
    }

    private fun getNewSeasons() = viewModelScope.launch(Dispatchers.IO) {
        _animeList.postValue(ResultWrapper.Loading())
        val response = repo.parseNewSeasons()
        response.collect {
            if (it is ResultWrapper.Success){
                val model = HomeMainModel(
                    typeValue = HomeTypes.NEW_SEASON,
                    animeList = it.data as List<AnimeModel>)
                _list.add(model)
                _animeList.postValue(ResultWrapper.Success(_list))
            }
            else
                _animeList.postValue(ResultWrapper.Error(it.message.toString()))
        }
    }
}