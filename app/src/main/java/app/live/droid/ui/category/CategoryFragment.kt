package app.live.droid.ui.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import app.live.droid.R
import app.live.droid.databinding.FragmentCategoryBinding
import app.live.droid.logic.model.Category
import app.live.droid.logic.model.CategoryChild
import com.drake.brv.layoutmanager.HoverGridLayoutManager
import com.drake.brv.utils.bindingAdapter
import com.drake.brv.utils.setup

class CategoryFragment : Fragment() {

    private lateinit var viewModel: CategoryViewModel

    private var _binding: FragmentCategoryBinding? = null


    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this).get(CategoryViewModel::class.java)
        _binding = FragmentCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val layoutManager = HoverGridLayoutManager(requireContext(), 5) // 2 则代表列表一行铺满要求跨度为2
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                if (position < 0) return 1 // 如果添加分割线可能导致position为负数
                // 根据类型设置列表item跨度
                return when (binding.rv.bindingAdapter.getItemViewType(position)) {
                    R.layout.item_category_child -> 1 // 设置指定类型的跨度为1, 假设spanCount为2则代表此类型占据宽度为二分之一
                    else -> 5
                }
            }
        }

        binding.rv.layoutManager = layoutManager

        binding.rv.setup {
            expandAnimationEnabled = false
            addType<Category>(R.layout.item_category)
            addType<CategoryChild>(R.layout.item_category_child)
            R.id.item.onFastClick {
                expandOrCollapse()
            }

        }.models = getData()

    }

    private fun getData(): MutableList<Category> {
        return mutableListOf<Category>().apply {
            repeat(10) {
                add(Category())
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}