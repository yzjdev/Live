package app.live.droid.ui.recommend

import android.os.Bundle
import android.util.TypedValue
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import app.live.droid.BaseFragment
import app.live.droid.databinding.FragmentRecommendBinding
import app.live.droid.databinding.ItemLiveBinding
import app.live.droid.extensions.loadUrl
import app.live.droid.logic.model.LiveBean
import app.live.droid.parser.LiveParser
import app.live.droid.ui.player.PlayActivity
import com.drake.brv.utils.models
import com.drake.brv.utils.setup
import kotlin.reflect.KClass


class RecommendFragment constructor(private val liveParser: LiveParser?) :
    BaseFragment<FragmentRecommendBinding, RecommendViewModel>() {

    private var page = 1

    init {
        flag = true
    }

    override fun getViewModel(): RecommendViewModel {
        return ViewModelProvider(
            this,
            RecommendViewModelFactory(liveParser!!)
        )[RecommendViewModel::class.java]
    }

    override fun getViewModelClass(): KClass<RecommendViewModel> {
        return RecommendViewModel::class
    }

    override fun getViewBindingClass(): Class<FragmentRecommendBinding> {
        return FragmentRecommendBinding::class.java
    }

    override fun initData() {
        super.initData()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.refresh.setOnRefreshListener {
            model.getLives(page)
            binding.refresh.isRefreshing = false
        }
        binding.rv.layoutManager = GridLayoutManager(activity, 2)
        binding.rv.setup {
            addType<LiveBean>(app.live.droid.R.layout.item_live)

            onBind {
                val b = getBinding<ItemLiveBinding>()
                val screenWidth =
                    resources.displayMetrics.widthPixels / 2 - TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP,
                        16f,
                        resources.displayMetrics
                    )
                val targetHeight = screenWidth * 95 / 169f
                b.preview.layoutParams.height = targetHeight.toInt()

                val data = getModel<LiveBean>()
                b.apply {
                    room.text = data.name
                    title.text = data.title
                    game.text = data.gameName
                    avatar.loadUrl(data.avatar)
                    preview.loadUrl(data.coverUrl)
                    num.text = data.num
                }

                itemView.setOnClickListener {
                    PlayActivity.actionStart(requireContext(), liveParser!!, data)
                }


            }
        }.models = mutableListOf()

        model.getLives(page)
        model.liveLiveData.observe(viewLifecycleOwner, Observer { result ->
            val lives = result.getOrNull()
            val a = ArrayList<LiveBean>()
            if (lives != null) {
                a.addAll(lives)
                binding.rv.models = a
            } else {

            }
        })

    }
}