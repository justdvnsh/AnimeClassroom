package divyansh.tech.animeclassroom.animeDetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import divyansh.tech.animeclassroom.ResultWrapper
import divyansh.tech.animeclassroom.models.home.AnimeDetailModel
import divyansh.tech.animeclassroom.models.home.AnimeModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class AnimeDetailViewModel @Inject constructor(
//    private
): ViewModel(){

    private val _animeDetailLiveData: MutableLiveData<ResultWrapper<AnimeDetailModel>> = MutableLiveData()
    val animeDetailLiveData: LiveData<ResultWrapper<AnimeDetailModel>> get() = _animeDetailLiveData

    fun getAnimeDetails(animeModel: AnimeModel) = viewModelScope.launch(Dispatchers.IO) {}
}