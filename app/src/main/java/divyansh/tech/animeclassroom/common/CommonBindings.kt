package divyansh.tech.animeclassroom.common

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import divyansh.tech.animeclassroom.common.utils.C.MANGA_URL
import divyansh.tech.animeclassroom.di.GlideApp

/*
* Load Images into imageViews
* */
@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, url: String) {
    Log.i("IMAGE URL -> ", url)
    GlideApp.with(view.context).load(url).into(view)
}

/*
* Load Images into imageViews
* */
@BindingAdapter("mangaImageUrl")
fun loadMangaImage(view: ImageView, url: String) {
    Log.i("IMAGE URL -> ", url)
    GlideApp.with(view.context).load(MANGA_URL + url).into(view)
}

@BindingAdapter("setErrorImage")
fun setErrorImage(view: AppCompatImageView, loadingObj: LoadingModel?) {
    loadingObj?.let {
        val drawable = ContextCompat.getDrawable(view.context, it.errorImageID)
        Glide.with(view).load(drawable).into(view)
    }
}

@BindingAdapter("setErrorText")
fun setErrorText(view: TextView, loadingObj: LoadingModel?) {
    loadingObj?.let {
        view.text = view.context.resources.getString(it.errorMsg)
    }
}

@BindingAdapter("updateLoadingErrorRootViewVisibility")
fun updateLoadingErrorRootViewVisibility(view: View, loadingObj: LoadingModel?) {
    loadingObj?.let {
        if (it.loading != CommonViewModel.Loading.COMPLETED && it.isListEmpty) {
            view.visibility = View.VISIBLE
        } else {
            view.visibility = View.GONE
        }
    }
}

@BindingAdapter("updateLoadingState")
fun updateLoadingState(view: ProgressBar, loadingObj: LoadingModel?) {
    if (loadingObj?.loading == CommonViewModel.Loading.LOADING && loadingObj.isListEmpty) {
        view.visibility = View.VISIBLE
    } else {
        view.visibility = View.GONE
    }
}

@BindingAdapter("updateSwipeRefresh")
fun updateSwipeRefresh(view: SwipeRefreshLayout, loadingObj: LoadingModel?) {
    if (loadingObj?.loading != CommonViewModel.Loading.LOADING) {
        view.isRefreshing = false
    }
}

@BindingAdapter("updateSwipeRefreshWithProgressBar")
fun updateSwipeRefreshWithProgressBar(view: SwipeRefreshLayout, loadingObj: LoadingModel?) {
    view.isRefreshing =
        loadingObj?.loading == CommonViewModel.Loading.LOADING && !loadingObj.isListEmpty
}

@BindingAdapter("updateErrorState")
fun updateErrorState(view: ConstraintLayout, loadingObj: LoadingModel?) {
    if (loadingObj?.loading == CommonViewModel.Loading.ERROR && loadingObj.isListEmpty) {
        view.visibility = View.VISIBLE
    } else {
        view.visibility = View.GONE
    }
}