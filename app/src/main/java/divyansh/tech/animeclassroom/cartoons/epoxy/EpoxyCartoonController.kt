package divyansh.tech.animeclassroom.cartoons.epoxy

import com.airbnb.epoxy.TypedEpoxyController
import divyansh.tech.animeclassroom.common.data.Cartoons
import divyansh.tech.animeclassroom.cartoons.callbacks.CartoonClickCallback

class EpoxyCartoonController(
    private val callback: CartoonClickCallback
): TypedEpoxyController<ArrayList<Cartoons>>() {
    override fun buildModels(data: ArrayList<Cartoons>?) {
        data?.let {
            it.forEach {
                epoxyCartoon {
                    id(it.hashCode())
                    cartoons(it)
                    callback(callback)
                    spanSizeOverride { totalSpanCount, _, _ -> totalSpanCount / 2 }
                }
            }
        }
    }
}