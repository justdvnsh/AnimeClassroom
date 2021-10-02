package divyansh.tech.animeclassroom.common

import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import divyansh.tech.animeclassroom.C.BASE_URL

/*
* Load Images into imageViews
* */
@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, url: String) {
    Log.i("IMAGE URL -> ", url)
    Glide.with(view).load(url).into(view)
}