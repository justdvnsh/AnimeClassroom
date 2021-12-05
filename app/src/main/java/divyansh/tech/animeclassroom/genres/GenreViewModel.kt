package divyansh.tech.animeclassroom.genres

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import divyansh.tech.animeclassroom.common.utils.ResultWrapper
import divyansh.tech.animeclassroom.common.CommonViewModel
import divyansh.tech.animeclassroom.common.SearchScreenDataModel
import divyansh.tech.animeclassroom.di.DispatcherModule
import divyansh.tech.animeclassroom.genres.source.GenreSearchDefaultRepo
import divyansh.tech.animeclassroom.common.data.Manga
import divyansh.tech.animeclassroom.common.data.AnimeModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GenreViewModel @Inject constructor(
    private val repo: GenreSearchDefaultRepo,
    @DispatcherModule.IODispatcher private val coroutineDispatcher: CoroutineDispatcher
): CommonViewModel(){

    private val _genreSearchLiveData = MutableLiveData<SearchScreenDataModel?>()
    val genreSearchLiveData get() = _genreSearchLiveData

    fun searchGenre(genre: String) {
        viewModelScope.launch(coroutineDispatcher) {
            val response = repo.genreSearchManga(genre)
            val responseAnime = repo.genreSearchAnime(genre)
            if (response is ResultWrapper.Success) {
                if (responseAnime is ResultWrapper.Success) {
                    _genreSearchLiveData.postValue(
                        SearchScreenDataModel(
                        mangas = response.data as ArrayList<Manga>,
                        animes = responseAnime.data as ArrayList<AnimeModel>
                    ))
                } else _genreSearchLiveData.postValue(
                    SearchScreenDataModel(
                    mangas = response.data as ArrayList<Manga>,
                        animes = arrayListOf()
                ))
            } else _genreSearchLiveData.postValue(null)
        }
    }
}