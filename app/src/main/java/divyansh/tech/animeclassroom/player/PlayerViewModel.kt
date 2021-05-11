package divyansh.tech.animeclassroom.player

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import divyansh.tech.animeclassroom.ResultWrapper
import divyansh.tech.animeclassroom.models.home.PlayerScreenModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayerViewModel @Inject constructor(
    private val playerRepo: PlayerRepo
): ViewModel() {

    private val _streamingUrlLiveData: MutableLiveData<ResultWrapper<PlayerScreenModel>> =
        MutableLiveData()
    val streamingLiveData: LiveData<ResultWrapper<PlayerScreenModel>> get() = _streamingUrlLiveData

    fun getStreamingUrl(episodeUrl: String) = viewModelScope.launch(Dispatchers.IO) {
        Log.i("Player","https://www1.gogoanime.ai$episodeUrl")
        when (val response = playerRepo.getEpisodeDetails("https://www1.gogoanime.ai$episodeUrl")) {
            is ResultWrapper.Success -> {
                Log.i("Player-reponse", response.data.toString())
                _streamingUrlLiveData.postValue(ResultWrapper.Success(response.data as PlayerScreenModel))
            }
            else -> _streamingUrlLiveData.postValue(ResultWrapper.Error(message = response.message!!, data = null))
        }
    }
}