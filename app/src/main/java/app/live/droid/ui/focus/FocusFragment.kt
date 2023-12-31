package app.live.droid.ui.focus

import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import app.live.droid.base.BaseFragment
import app.live.droid.databinding.FragmentFocusBinding
import app.live.droid.databinding.ItemLiveBinding
import app.live.droid.logic.model.LiveBean
import com.drake.brv.utils.setup

class FocusFragment : BaseFragment<FragmentFocusBinding, FocusViewModel>() {

    override fun createViewModelClass() = FocusViewModel::class.java

    override fun createViewBinding(inflater: LayoutInflater, container: ViewGroup?) = FragmentFocusBinding.inflate(inflater,container,false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rv.layoutManager = GridLayoutManager(activity, 2)
        binding.rv.setup {
            addType<LiveBean>(app.live.droid.R.layout.item_live)
            onBind {
                val b = getBinding<ItemLiveBinding>()
                val screenWidth = resources.displayMetrics.widthPixels / 2 - TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, 16f, resources.displayMetrics)
                val targetHeight = screenWidth * 95 / 169f
                b.preview.layoutParams.height = targetHeight.toInt()
            }
        }.models = mutableListOf()
    }

}