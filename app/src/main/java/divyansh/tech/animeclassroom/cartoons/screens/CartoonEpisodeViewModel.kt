package divyansh.tech.animeclassroom.cartoons.screens

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import divyansh.tech.animeclassroom.ResultWrapper
import divyansh.tech.animeclassroom.cartoon.CartoonDefaultRepo
import divyansh.tech.animeclassroom.cartoonModels.Cartoons
import divyansh.tech.animeclassroom.common.CommonViewModel
import divyansh.tech.animeclassroom.di.DispatcherModule
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class CartoonEpisodeViewModel @Inject constructor(
    private val repo: CartoonDefaultRepo,
    @DispatcherModule.IODispatcher private val coroutineDispatcher: CoroutineDispatcher
): CommonViewModel() {

    private val _episodesLiveData = MutableLiveData<ArrayList<Cartoons>>()
    val episodesLiveData get() = _episodesLiveData

    fun getEpisodes(url: String) = viewModelScope.launch(coroutineDispatcher) {
        when(val response = repo.getEpisodes(url)) {
            is ResultWrapper.Success -> _episodesLiveData.postValue(response.data as ArrayList<Cartoons>)
            is ResultWrapper.Error -> Log.i("Cartoons","Something went wrong")
            else -> {}
        }
    }
}