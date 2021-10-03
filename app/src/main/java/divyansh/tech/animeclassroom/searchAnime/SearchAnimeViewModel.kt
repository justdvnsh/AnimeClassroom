package divyansh.tech.animeclassroom.searchAnime

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import divyansh.tech.animeclassroom.ResultWrapper
import divyansh.tech.animeclassroom.animeSearch.AnimeSearchRepo
import divyansh.tech.animeclassroom.common.CommonViewModel
import divyansh.tech.animeclassroom.models.home.AnimeModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchAnimeViewModel @Inject constructor(
    private val animeSearchRepo: AnimeSearchRepo
): CommonViewModel() {

    private val _searchAnimeLiveData: MutableLiveData<ResultWrapper<ArrayList<AnimeModel>>> = MutableLiveData()
    val searchAnimeLiveData: LiveData<ResultWrapper<ArrayList<AnimeModel>>> get() = _searchAnimeLiveData

    private val _invalidAnimeSearch: MutableLiveData<Boolean> = MutableLiveData(false)
    val invalidAnimeSearch: LiveData<Boolean> get() = _invalidAnimeSearch

    fun searchAnime(anime: String) = viewModelScope.launch(Dispatchers.IO) {
        when (val response = animeSearchRepo.searchAnimes(anime)) {
            is ResultWrapper.Success ->
            {
                Log.i("Search", response.data.toString())
                if(response.data.toString()=="[]"){
                    _invalidAnimeSearch.postValue(true)
                }else{
                    _invalidAnimeSearch.postValue(false)
                    _searchAnimeLiveData.postValue(ResultWrapper.Success(response.data as ArrayList<AnimeModel>))
                }

            }
            else ->
                _searchAnimeLiveData.postValue(ResultWrapper.Error(message = response.message!!, data = null))
        }
    }
}