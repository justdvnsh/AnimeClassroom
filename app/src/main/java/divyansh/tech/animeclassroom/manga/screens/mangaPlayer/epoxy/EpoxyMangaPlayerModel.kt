package divyansh.tech.animeclassroom.manga.screens.mangaPlayer.epoxy

import androidx.databinding.ViewDataBinding
import com.airbnb.epoxy.DataBindingEpoxyModel
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.databinding.BR
import divyansh.tech.animeclassroom.R

@EpoxyModelClass(layout = R.layout.recycler_manga_player_item)
abstract class EpoxyMangaPlayerModel: DataBindingEpoxyModel() {
    @EpoxyAttribute
    lateinit var imageUrl: String

    override fun setDataBindingVariables(binding: ViewDataBinding) {
        binding.setVariable(BR.imageUrl, imageUrl)
    }
}