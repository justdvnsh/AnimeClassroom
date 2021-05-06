package divyansh.tech.animeclassroom.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import divyansh.tech.animeclassroom.ResultWrapper
import divyansh.tech.animeclassroom.models.home.AnimeModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repo: HomeDefaultRepo
): ViewModel() {

    private val _popularAnimeLiveData: MutableLiveData<ResultWrapper<List<AnimeModel>>> = MutableLiveData()
    val popularAnimeLiveData: LiveData<ResultWrapper<List<AnimeModel>>> get() = _popularAnimeLiveData

    init {
        getPopularAnimes()
    }

    private fun getPopularAnimes() = viewModelScope.launch(Dispatchers.IO) {
        _popularAnimeLiveData.postValue(ResultWrapper.Loading())
        val response = repo.parsePopularAnimes()
        response.collect {
            if (it is ResultWrapper.Success)
                _popularAnimeLiveData.postValue(ResultWrapper.Success(it.data as List<AnimeModel>))
            else
                _popularAnimeLiveData.postValue(ResultWrapper.Error(it.message.toString()))
        }
    }
}