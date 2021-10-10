package divyansh.tech.animeclassroom.manga.screens.mangaPlayer

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import divyansh.tech.animeclassroom.ResultWrapper
import divyansh.tech.animeclassroom.common.CommonViewModel
import divyansh.tech.animeclassroom.di.DispatcherModule
import divyansh.tech.animeclassroom.mangaPlayer.MangaPlayerDefaultRepo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MangaPlayerViewModel @Inject constructor(
    private val repo: MangaPlayerDefaultRepo,
    @DispatcherModule.IODispatcher private val coroutineDispatcher: CoroutineDispatcher
): CommonViewModel() {

    private val _mangaChapterItemsLiveData = MutableLiveData<ArrayList<String>>()
    val mangaChapterItemLiveData get() = _mangaChapterItemsLiveData

    fun getChapterItems(url: String) {
        viewModelScope.launch(coroutineDispatcher) {
            val response = repo.getMangaChapter(url)
            response.collect {
                if (it is ResultWrapper.Success)
                    _mangaChapterItemsLiveData.postValue(it.data as ArrayList<String>)
                else Log.i("MANGA PLAYER -> ", it.message.toString())
            }
        }
    }
}