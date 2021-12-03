package divyansh.tech.animeclassroom.manga.screens.mangaDetail

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import divyansh.tech.animeclassroom.common.utils.ResultWrapper
import divyansh.tech.animeclassroom.common.CommonViewModel
import divyansh.tech.animeclassroom.di.DispatcherModule
import divyansh.tech.animeclassroom.manga.screens.mangaDetail.source.MangaDetailDefaultRepo
import divyansh.tech.animeclassroom.common.data.mangaModels.MangaDetail
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MangaDetailViewModel @Inject constructor(
    private val repo: MangaDetailDefaultRepo,
    @DispatcherModule.IODispatcher private val coroutineDispatcher: CoroutineDispatcher
): CommonViewModel() {

    private val _mangaDetailLiveData = MutableLiveData<MangaDetail>()
    val mangaDetailLiveData get() = _mangaDetailLiveData

    fun getMangaDetails(url: String) {
        viewModelScope.launch(coroutineDispatcher) {
            val response = repo.getMangaDetails(url)
            response.collect {
                if (it is ResultWrapper.Success) {
                    _mangaDetailLiveData.postValue(it.data as MangaDetail)
                } else Log.i("MANGA DETAIL -> ", it.message.toString())
            }
        }
    }
}