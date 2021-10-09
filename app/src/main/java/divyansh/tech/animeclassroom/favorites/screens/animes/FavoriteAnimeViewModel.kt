package divyansh.tech.animeclassroom.favorites.screens.animes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import divyansh.tech.animeclassroom.ResultWrapper
import divyansh.tech.animeclassroom.common.CommonViewModel
import divyansh.tech.animeclassroom.di.DispatcherModule
import divyansh.tech.animeclassroom.favorites.FavoriteAnimeLocalRepo
import divyansh.tech.animeclassroom.models.home.OfflineAnimeModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteAnimeViewModel @Inject constructor(
    favoriteAnimeLocalRepo: FavoriteAnimeLocalRepo,
    @DispatcherModule.IODispatcher coroutineDispatcher: CoroutineDispatcher
): CommonViewModel() {

    private val _favoriteAnimeListState: MutableLiveData<ResultWrapper<List<OfflineAnimeModel>>> =
        MutableLiveData()
    val favoriteAnimeListState: LiveData<ResultWrapper<List<OfflineAnimeModel>>> = _favoriteAnimeListState

    init {
        viewModelScope.launch(coroutineDispatcher) {
            _favoriteAnimeListState.postValue(ResultWrapper.Loading())
            _favoriteAnimeListState.postValue(
                ResultWrapper.Success(favoriteAnimeLocalRepo.getAllAnime())
            )
        }
    }
}