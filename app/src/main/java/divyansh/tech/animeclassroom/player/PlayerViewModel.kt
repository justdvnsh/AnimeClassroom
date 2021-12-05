package divyansh.tech.animeclassroom.player

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import divyansh.tech.animeclassroom.common.utils.C.BASE_URL
import divyansh.tech.animeclassroom.common.utils.ResultWrapper
import divyansh.tech.animeclassroom.common.CommonViewModel
import divyansh.tech.animeclassroom.common.data.PlayerScreenModel
import divyansh.tech.animeclassroom.player.source.PlayerRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayerViewModel @Inject constructor(
    private val playerRepo: PlayerRepo
): CommonViewModel() {

    private val _streamingUrlLiveData: MutableLiveData<ResultWrapper<PlayerScreenModel>> =
        MutableLiveData()
    val streamingLiveData: LiveData<ResultWrapper<PlayerScreenModel>> get() = _streamingUrlLiveData

    private val _animeName: MutableLiveData<String> = MutableLiveData()
    val animeName: LiveData<String> get() = _animeName

    private val _clickControlLiveData: MutableLiveData<PlayerClick> = MutableLiveData()
    val clickControlLiveData: LiveData<PlayerClick> get() = _clickControlLiveData

    fun getStreamingUrl(episodeUrl: String) = viewModelScope.launch(Dispatchers.IO) {
        _streamingUrlLiveData.postValue(ResultWrapper.Loading())
        val url = if (!episodeUrl.startsWith(BASE_URL)) BASE_URL + episodeUrl else episodeUrl
        Log.i("Player", url)
        val response = playerRepo.getEpisodeDetails(url)
        response.collect {
            when (it) {
                is ResultWrapper.Success -> {
                    Log.i("Player-Response", it.data.toString())
                    _streamingUrlLiveData.postValue(ResultWrapper.Success(it.data as PlayerScreenModel))
                    _animeName.postValue((it.data as PlayerScreenModel).animeName)
                }
                else -> {
                    Log.i("Player-Response", it.data.toString())
                    _streamingUrlLiveData.postValue(ResultWrapper.Error(message = it.message!!, data = null))
                }
            }
        }
    }

    fun getCartoonStreamingUrl(url: String) = viewModelScope.launch(Dispatchers.IO) {
        val response = playerRepo.getCartoonStreamingUrl(url)
        response.collect {
            when (it) {
                is ResultWrapper.Success -> _streamingUrlLiveData.postValue(ResultWrapper.Success(it.data as PlayerScreenModel))
                is ResultWrapper.Error -> {
                    Log.i("Player-Response", it.data.toString())
                    _streamingUrlLiveData.postValue(ResultWrapper.Error(message = it.message!!, data = null))
                }
                else -> {}
            }
        }
    }

    fun updateButtonClick(clickType: PlayerClick) {
        _clickControlLiveData.value = clickType
    }

    enum class PlayerClick {
        BACK, FULLSCREEN_TOGGLE, SPEED_CONTROL, QUALITY_CONTROL;
    }

    enum class PlayerTypes {
        ANIME, CARTOON
    }
}