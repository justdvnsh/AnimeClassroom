package divyansh.tech.animeclassroom.common.utils

sealed class ResultWrapper<T> (
    val data: T? = null,
    val message: String? = null
) {

    class Success<T>(data: T): ResultWrapper<T>(data)
    class Error<T>(message: String, data: T? = null): ResultWrapper<T>(data, message)
    class Loading<T>: ResultWrapper<T>()
}