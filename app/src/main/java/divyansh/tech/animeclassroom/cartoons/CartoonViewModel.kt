package divyansh.tech.animeclassroom.cartoons

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import divyansh.tech.animeclassroom.ResultWrapper
import divyansh.tech.animeclassroom.cartoon.CartoonDataSource
import divyansh.tech.animeclassroom.cartoon.CartoonDefaultRepo
import divyansh.tech.animeclassroom.cartoonModels.Cartoons
import divyansh.tech.animeclassroom.common.CommonViewModel
import divyansh.tech.animeclassroom.di.DispatcherModule
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartoonViewModel @Inject constructor(
    private val repo: CartoonDefaultRepo,
    @DispatcherModule.IODispatcher private val coroutineDispatcher: CoroutineDispatcher
): CommonViewModel() {

    private val _cartoonsLiveData = MutableLiveData<ArrayList<Cartoons>>()
    val cartoonsLiveData get() = _cartoonsLiveData

    init {
        getCartoons()
    }

    private fun getCartoons() = viewModelScope.launch(coroutineDispatcher) {
        when(val response = repo.getCartoons()) {
            is ResultWrapper.Success -> _cartoonsLiveData.postValue(response.data as ArrayList<Cartoons>)
            is ResultWrapper.Error -> Log.i("Cartoons","Something went wrong")
            else -> {}
        }
    }
}