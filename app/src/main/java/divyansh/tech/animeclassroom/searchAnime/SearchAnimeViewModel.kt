package divyansh.tech.animeclassroom.searchAnime

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import divyansh.tech.animeclassroom.common.utils.ResultWrapper
import divyansh.tech.animeclassroom.searchAnime.source.AnimeSearchRepo
import divyansh.tech.animeclassroom.common.CommonViewModel
import divyansh.tech.animeclassroom.common.SearchScreenDataModel
import divyansh.tech.animeclassroom.di.DispatcherModule
import divyansh.tech.animeclassroom.common.data.mangaModels.Manga
import divyansh.tech.animeclassroom.common.data.home.AnimeModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchAnimeViewModel @Inject constructor(
    private val animeSearchRepo: AnimeSearchRepo,
    @DispatcherModule.IODispatcher private val coroutineDispatcher: CoroutineDispatcher
) : CommonViewModel() {

    private val _searchAnimeLiveData: MutableLiveData<ResultWrapper<SearchScreenDataModel>> =
        MutableLiveData()
    val searchAnimeLiveData get() = _searchAnimeLiveData

    fun searchAnime(keyword: String){
        viewModelScope.launch(coroutineDispatcher) {
            val responseManga = animeSearchRepo.searchManga(keyword)
            val responseAnime = animeSearchRepo.searchAnimes(keyword)
            if (responseManga is ResultWrapper.Success) {
                if (responseAnime is ResultWrapper.Success) {
                    _searchAnimeLiveData.postValue(
                        ResultWrapper.Success(
                            SearchScreenDataModel(
                                mangas = responseManga.data as ArrayList<Manga>,
                                animes = responseAnime.data as ArrayList<AnimeModel>
                            )
                        )
                    )
                } else ResultWrapper.Error("SOMETHING WENT WRONG", null)
            }else ResultWrapper.Error("SOMETHING WENT WRONG", null)
        }
    }
}