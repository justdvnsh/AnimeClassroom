package divyansh.tech.animeclassroom.cartoons

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import divyansh.tech.animeclassroom.common.utils.EventObserver
import divyansh.tech.animeclassroom.R
import divyansh.tech.animeclassroom.cartoons.callbacks.CartoonClickCallback
import divyansh.tech.animeclassroom.cartoons.epoxy.EpoxyCartoonController
import divyansh.tech.animeclassroom.databinding.FragmentCartoonsBinding

@AndroidEntryPoint
class CartoonFragment: Fragment() {

    private lateinit var _binding: FragmentCartoonsBinding

    private val viewModel by viewModels<CartoonViewModel>()

    private val controller by lazy {
        EpoxyCartoonController(CartoonClickCallback(viewModel))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartoonsBinding.inflate(inflater, container, false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding.mangaToolbar.title.text = getString(R.string.cartoons)
        setupRecyclerView()
        setupObservers()
    }

    private fun setupRecyclerView() {
        _binding.cartoonRv.apply {
            layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
            adapter = controller.adapter
            controller.spanCount = 2
        }
    }

    private fun setupObservers() {
        viewModel.cartoonsLiveData.observe(
            viewLifecycleOwner,
            Observer {
                controller.setData(it)
            }
        )

        viewModel.navigation.observe(
            viewLifecycleOwner,
            EventObserver {
                findNavController().navigate(it)
            }
        )
    }
}