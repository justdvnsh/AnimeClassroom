package divyansh.tech.animeclassroom.favorites

import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import divyansh.tech.animeclassroom.R


@AndroidEntryPoint
class DynamicTabFragment : Fragment(R.layout.fragment_dynamic_tab) {

    companion object {

        private const val CATEGORY = "category"
        fun getInstance(category: String) = DynamicTabFragment().apply {
            arguments = bundleOf(CATEGORY to category)
        }

    }

}