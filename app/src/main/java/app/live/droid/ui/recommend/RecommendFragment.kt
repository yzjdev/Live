package app.live.droid.ui.recommend

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import app.live.droid.MainActivity
import app.live.droid.base.BaseFragment
import app.live.droid.databinding.FragmentRecommendBinding
import app.live.droid.databinding.ItemLiveBinding
import app.live.droid.extensions.loadUrl
import app.live.droid.logic.model.LiveBean
import app.live.droid.parser.LiveParser
import app.live.droid.ui.player.PlayerActivity
import com.drake.brv.utils.addModels
import com.drake.brv.utils.models
import com.drake.brv.utils.setup


class RecommendFragment constructor(private val liveParser: LiveParser?) :
    BaseFragment<FragmentRecommendBinding, RecommendViewModel>() {

    private var page = 1

    init {
        flag = true
    }

    override fun getViewModel() = ViewModelProvider(this, RecommendViewModelFactory(liveParser!!))[RecommendViewModel::class.java]
    override fun getViewModelClass() = RecommendViewModel::class
    override fun getViewBindingClass() = FragmentRecommendBinding::class.java
    override fun initData() {
        super.initData()
    }


    val map = mutableMapOf<LiveParser, String>()

    @SuppressLint("RestrictedApi")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindRv()

        binding.fab.setOnClickListener {
            page++
            model.getLives(page)
        }

        model.getLives(1)
        model.liveLiveData.observe(viewLifecycleOwner, Observer { result ->
            val lives = result.getOrNull()
            if (lives != null) {
                if (page == 1) {
                    model.liveList.clear()
                }
                model.liveList.addAll(lives)
                binding.rv.addModels(lives)

                val hint = "正在直播 ${binding.rv.models?.size}"
                map[liveParser!!] = hint

                re()
            }
        })
    }


    fun bindRv() {
        binding.rv.layoutManager = GridLayoutManager(activity, 2)
        binding.rv.setup {

            addType<LiveBean>(app.live.droid.R.layout.item_live)

            onBind {
                val b = getBinding<ItemLiveBinding>()
                val screenWidth = resources.displayMetrics.widthPixels / 2 - TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16f, resources.displayMetrics)
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

                itemView.setOnClickListener { PlayerActivity.actionStart(requireContext(), liveParser!!, data) }
            }
        }
    }

    fun re() {
        val searchBar = (activity as MainActivity).getSearchBar()
        searchBar.hint = map[liveParser]
    }

    fun query(query: String) {
        binding.rv.models = model.liveList.filter { v ->
            when {
                query.isBlank() -> true
                else -> v.gameName.contains(query, true) ||
                        v.title.contains(query, true) ||
                        v.name.contains(query, true)
            }

        }
    }

}