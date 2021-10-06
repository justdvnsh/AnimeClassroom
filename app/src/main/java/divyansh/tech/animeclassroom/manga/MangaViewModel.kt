package divyansh.tech.animeclassroom.manga

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import divyansh.tech.animeclassroom.C.MANGA_URL
import divyansh.tech.animeclassroom.Manga.MangaHomeDefaultRepo
import divyansh.tech.animeclassroom.ResultWrapper
import divyansh.tech.animeclassroom.common.CommonViewModel
import divyansh.tech.animeclassroom.di.DispatcherModule
import divyansh.tech.animeclassroom.mangaModels.Manga
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class MangaViewModel @Inject constructor(
    private val repository: MangaHomeDefaultRepo,
    @DispatcherModule.IODispatcher val coroutineDispatcher: CoroutineDispatcher
): CommonViewModel(){

    private val _mangaListLiveData = MutableLiveData<ResultWrapper<ArrayList<Manga>>>()
    val mangaListLiveData get() = _mangaListLiveData

    init {
        getHomePage()
    }

    private fun getHomePage() {
        viewModelScope.launch(coroutineDispatcher) {
            val response = repository.getHomePageData(MANGA_URL)
            response.collect {
                when(it) {
                    is ResultWrapper.Success -> {
                        try {
                            val item = it.data as ArrayList<Manga>
                            _mangaListLiveData.postValue(ResultWrapper.Success(item))
                        } catch (e: Exception) {
                            _mangaListLiveData.postValue(ResultWrapper.Error("Could not parse"))
                        }
                    }
                    is ResultWrapper.Error -> Log.i("MANGA -> ", it.message.toString())
                    else -> {}
                }
            }
        }
    }
}