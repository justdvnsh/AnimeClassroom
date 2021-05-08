package divyansh.tech.animeclassroom.common

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

/*
* Load Images into imageViews
* */
@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, url: String) {
    Glide.with(view).load(url).into(view)
}