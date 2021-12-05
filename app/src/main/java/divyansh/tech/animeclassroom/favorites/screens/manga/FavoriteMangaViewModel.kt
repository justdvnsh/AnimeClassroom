package divyansh.tech.animeclassroom.favorites.screens.manga

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import divyansh.tech.animeclassroom.manga.source.MangaHomeLocalRepo
import divyansh.tech.animeclassroom.common.utils.ResultWrapper
import divyansh.tech.animeclassroom.common.CommonViewModel
import divyansh.tech.animeclassroom.di.DispatcherModule
import divyansh.tech.animeclassroom.common.data.Manga
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

//TODO: Implement FavoriteMangaLocalRepo and replace MangaHomeLocalRepo

@HiltViewModel
class FavoriteMangaViewModel @Inject constructor(
    favouriteMangaLocalRepo: MangaHomeLocalRepo,
    @DispatcherModule.IODispatcher coroutineDispatcher: CoroutineDispatcher
): CommonViewModel(){

    private val _favouriteMangaListState: MutableLiveData<ResultWrapper<List<Manga>>> =
        MutableLiveData()
    val favouriteMangaListState: LiveData<ResultWrapper<List<Manga>>> = _favouriteMangaListState

    init{
        viewModelScope.launch(coroutineDispatcher){
            _favouriteMangaListState.postValue(ResultWrapper.Loading())
            _favouriteMangaListState.postValue(ResultWrapper.Success(ArrayList()))
        }
    }


}