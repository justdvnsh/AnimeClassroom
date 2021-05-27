package divyansh.tech.animeclassroom.manga

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import divyansh.tech.animeclassroom.Manga.MangaHomeDefaultRepo
import divyansh.tech.animeclassroom.ResultWrapper
import divyansh.tech.animeclassroom.common.CommonViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MangaViewModel @Inject constructor(
    private val repository: MangaHomeDefaultRepo
): CommonViewModel(){

    private val _mangaListLiveData = MutableLiveData<ResultWrapper<*>>()
    val mangaListLiveData get() = _mangaListLiveData

    init {
        getHomePage()
    }

    private fun getHomePage() = viewModelScope.launch(Dispatchers.IO) {
        Log.i("MANGA", "INSIDE vIEW MODEL")
        val response = repository.getHomePageData("https://mangadex.tv/")
        Log.i("MANGA", response.toString())
        response.collect {
            when(it) {
                is ResultWrapper.Success -> _mangaListLiveData.postValue(it)
                is ResultWrapper.Error -> {}
                else -> {}
            }
        }
    }
}