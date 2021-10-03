package divyansh.tech.animeclassroom.common

import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import divyansh.tech.animeclassroom.di.GlideApp

/*
* Load Images into imageViews
* */
@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, url: String) {
    Log.i("IMAGE URL -> ", url)
    GlideApp.with(view.context).load(url).into(view)
}