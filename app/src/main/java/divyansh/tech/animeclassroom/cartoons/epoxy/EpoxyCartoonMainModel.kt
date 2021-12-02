package divyansh.tech.animeclassroom.cartoons.epoxy

import androidx.databinding.ViewDataBinding
import com.airbnb.epoxy.DataBindingEpoxyModel
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.databinding.BR
import divyansh.tech.animeclassroom.R
import divyansh.tech.animeclassroom.cartoonModels.Cartoons
import divyansh.tech.animeclassroom.cartoons.callbacks.CartoonClickCallback

@EpoxyModelClass(layout = R.layout.recycler_cartoon_item_view)
abstract class EpoxyCartoonModel(): DataBindingEpoxyModel() {
    @EpoxyAttribute
    lateinit var cartoons: Cartoons

    @EpoxyAttribute
    lateinit var callback: CartoonClickCallback

    override fun setDataBindingVariables(binding: ViewDataBinding) {
        binding.setVariable(BR.cartoon, cartoons)
        binding.setVariable(BR.callback, callback)
    }
}