package divyansh.tech.animeclassroom.common

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
import divyansh.tech.animeclassroom.Event
import divyansh.tech.animeclassroom.R
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

open class CommonViewModel: ViewModel() {

    private val _loadingModel: MutableLiveData<LoadingModel> = MutableLiveData()
    val loadingModel: LiveData<LoadingModel> = _loadingModel
    private var errorCode: Error = Error.ERROR_CODE_DEFAULT
    private var errorMsgId = R.string.status_code_default
    private var errorImageId = R.drawable.no_internet_connection1

    // Clicked events
    private val _navigationLiveData: MutableLiveData<Event<NavDirections>> = MutableLiveData()
    val navigation: LiveData<Event<NavDirections>> get() = _navigationLiveData
    private val _startPlayer = MutableLiveData(false)
    val startPlayer get() = _startPlayer
    private var _episodeUrl = ""
    val episodeUrl get() = _episodeUrl



    private fun updateErrorModel(e: Throwable?) {
        e?.let {
            when (e) {
                is SocketException, is UnknownHostException, is SocketTimeoutException -> {
                    errorCode = Error.NO_INTERNET_CONNECTION
                    errorMsgId = R.string.no_internet
                    errorImageId = R.drawable.no_internet_connection1
                }
                else -> {
                    errorImageId = R.drawable.no_internet_connection1
                    errorCode = Error.ERROR_CODE_DEFAULT
                }
            }
        }
    }

    protected fun isLoading(): Boolean {
        _loadingModel.value?.let {
            return it.loading == Loading.LOADING
        } ?: return false
    }

    fun updateLoadingState(loading: Loading, e: Throwable?, isListEmpty: Boolean = true) {
        updateErrorModel(e)
        _loadingModel.postValue(
            LoadingModel(
                loading = loading,
                errorCode = errorCode,
                errorMsg = errorMsgId,
                isListEmpty = isListEmpty,
                errorImageID = errorImageId

            )
        )
    }

    /*
   * Helper functions -> Events
   * */
    fun changeNavigation(navDirections: NavDirections) {
        Log.d(TAG, "changeNavigation: $navDirections")
        _navigationLiveData.value = Event(navDirections)
    }

    fun startPlayerWithNewIntent(episodeUrl: String) {
        this._episodeUrl=episodeUrl
        _startPlayer.value = true
        _startPlayer.value = false
    }

    enum class Error {
        RESPONSE_UNKNOWN, ERROR_CODE_DEFAULT, NO_INTERNET_CONNECTION
    }

    enum class Loading {
        LOADING, COMPLETED, ERROR
    }

    companion object {
        private const val TAG = "CommonViewModel"
    }
}