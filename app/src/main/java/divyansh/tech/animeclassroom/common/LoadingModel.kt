package divyansh.tech.animeclassroom.common

data class LoadingModel(
    val loading: CommonViewModel.Loading,
    val isListEmpty: Boolean,
    val errorCode: CommonViewModel.Error,
    val errorMsg: Int,
    val errorImageID: Int
)