package divyansh.tech.animeclassroom.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import divyansh.tech.animeclassroom.common.utils.ResultWrapper
import divyansh.tech.animeclassroom.common.CommonViewModel
import divyansh.tech.animeclassroom.di.DispatcherModule
import divyansh.tech.animeclassroom.home.dataModels.*
import divyansh.tech.animeclassroom.home.source.HomeDefaultRepo
import divyansh.tech.animeclassroom.common.utils.HomeTypes
import divyansh.tech.animeclassroom.common.data.home.AnimeModel
import divyansh.tech.animeclassroom.common.data.home.GenreModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repo: HomeDefaultRepo,
    @DispatcherModule.IODispatcher val coroutineDispatcher: CoroutineDispatcher
): CommonViewModel() {

    private val _animeList: MutableLiveData<ArrayList<HomeMainModel>> = MutableLiveData()
    val animeList: LiveData<ArrayList<HomeMainModel>> get() = _animeList

    private val _list: ArrayList<HomeMainModel> = arrayListOf()

    init {
        getRecentReleases()
        getPopularAnimes()
        getNewSeasons()
        getPopularMovies()
        getGenres()
    }

    private fun getPopularAnimes() {
        viewModelScope.launch(coroutineDispatcher) {
            updateLoadingState(Loading.LOADING, null, isListEmpty())
            val response = repo.parsePopularAnimes()
            response.collect {
                if (it is ResultWrapper.Success) {
                    val model = HomeMainModel(
                        type = HomeTypes.POPULAR_ANIME,
                        feedResult = it.data as ArrayList<AnimeModel>)
                    Log.i("ANIME LIST -> ", model.toString())
                    _list.add(model)
                    Log.i("ANIME LIST -> ", _list.toString())
                    _animeList.postValue(_list)
                    Log.i("ANIME LIST EMPTY -> ", isListEmpty().toString())
                    updateLoadingState(Loading.COMPLETED, null, isListEmpty())
                }
                else
                    updateLoadingState(Loading.ERROR, Exception("Something Went Wrong"), isListEmpty())
            }
        }
    }

    private fun getRecentReleases() {
        viewModelScope.launch(coroutineDispatcher) {
            updateLoadingState(Loading.LOADING, null, isListEmpty())
            val response = repo.parseRecentReleases()
            response.collect {
                if (it is ResultWrapper.Success){
                    val model = HomeMainModel(
                        type = HomeTypes.RECENT_RELEASE,
                        feedResult = it.data as ArrayList<AnimeModel>)
                    _list.add(model)
                    _animeList.postValue(_list)
                    updateLoadingState(Loading.COMPLETED, null, isListEmpty())
                }
                else
                    updateLoadingState(Loading.ERROR, Exception("Something Went Wrong"), isListEmpty())
            }
        }
    }

    private fun getPopularMovies() {
        viewModelScope.launch(coroutineDispatcher) {
            updateLoadingState(Loading.LOADING, null, isListEmpty())
            val response = repo.parsePopularMovies()
            response.collect {
                if (it is ResultWrapper.Success){
                    val model = HomeMainModel(
                        type = HomeTypes.POPULAR_MOVIES,
                        feedResult = it.data as ArrayList<AnimeModel>)
                    _list.add(model)
                    _animeList.postValue(_list)
                    updateLoadingState(Loading.COMPLETED, null, isListEmpty())
                }
                else
                    updateLoadingState(Loading.ERROR, Exception("Something Went Wrong"), isListEmpty())
            }
        }
    }

    private fun getNewSeasons() {
        viewModelScope.launch(coroutineDispatcher) {
            updateLoadingState(Loading.LOADING, null, isListEmpty())
            val response = repo.parseNewSeasons()
            response.collect {
                if (it is ResultWrapper.Success){
                    val model = HomeMainModel(
                        type = HomeTypes.NEW_SEASON,
                        feedResult = it.data as ArrayList<AnimeModel>)
                    _animeList.postValue(_list)
                    updateLoadingState(Loading.COMPLETED, null, isListEmpty())
                }
                else
                    updateLoadingState(Loading.ERROR, Exception("Something Went Wrong"), isListEmpty())
            }
        }
    }

    private fun getGenres() {
        viewModelScope.launch(coroutineDispatcher) {
            updateLoadingState(Loading.LOADING, null, isListEmpty())
            val response = repo.parseGenres()
            response.collect {
                if (it is ResultWrapper.Success){
                    val model = HomeMainModel(
                        type = HomeTypes.GENRES,
                        feedResult = it.data as ArrayList<GenreModel>)
                    _list.add(model)
                    _animeList.postValue(_list)
                    updateLoadingState(Loading.COMPLETED, null, isListEmpty())
                }
                else
                    updateLoadingState(Loading.ERROR, Exception("Something Went Wrong"), isListEmpty())
            }
        }
    }

    private fun isListEmpty(): Boolean {
        return _list.isNullOrEmpty()
    }
}