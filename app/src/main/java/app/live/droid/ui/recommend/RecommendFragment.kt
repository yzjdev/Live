package app.live.droid.ui.recommend

import android.content.Context
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.live.droid.R
import app.live.droid.base.BaseFragment
import app.live.droid.databinding.FragmentRecommendBinding
import app.live.droid.databinding.ItemLiveBinding
import app.live.droid.extensions.gson
import app.live.droid.extensions.loadUrl
import app.live.droid.logic.model.LiveBean
import app.live.droid.parser.LiveParser
import app.live.droid.parser.platform.Douyu
import app.live.droid.parser.platform.Huya
import app.live.droid.parser.platform.Kuaishou
import app.live.droid.ui.player.PlayerActivity
import com.alibaba.fastjson2.JSON
import com.drake.brv.PageRefreshLayout
import com.drake.brv.utils.models
import com.drake.brv.utils.setup
import com.google.gson.reflect.TypeToken
import kotlin.math.abs


class RecommendFragment constructor(private val liveParser: LiveParser?) : BaseFragment<FragmentRecommendBinding, RecommendViewModel>(true) {

    private val routeName = when (liveParser) {
        is Huya -> "huya"
        is Douyu -> "douyu"
        is Kuaishou -> "kuaishou"
        else -> ""
    }

    private var page = 1

    val mapName = "${routeName}_$page"

    val map = mutableMapOf<LiveParser, String>()

    override fun createCustomViewModelIfNeed() = RecommendViewModelFactory(liveParser!!)

    override fun createViewModelClass() = RecommendViewModel::class.java

    override fun createViewBinding(inflater: LayoutInflater, container: ViewGroup?) = FragmentRecommendBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindRv()
        binding.forceRefresh.setOnClickListener { refresh() }

        viewModel.liveLiveData.observe(viewLifecycleOwner, Observer { result ->
            val lives = result.getOrNull()
            if (lives != null) {
                viewModel.liveListMap[mapName] = lives
                val hint = "正在直播 ${binding.rv.models?.size}"
                map[liveParser!!] = hint

                val json = JSON.toJSONString(lives)
                activity?.apply {
                    val editor = getSharedPreferences(routeName, Context.MODE_PRIVATE).edit()
                    editor.putString("$page", json)
                    editor.apply()
                }
            }
        })
    }

    val scrollOffset = 4
    private fun bindRv() {
        binding.rv.setHasFixedSize(true)
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

        val fab = binding.fab

        binding.search.doAfterTextChanged {
            val s = it.toString()
            query(s)
        }

        fab.setOnClickListener {
            val rv = activity?.findViewById<RecyclerView>(R.id.rv)
            rv?.scrollToPosition(0)
            fab.hide()
        }
        fab.hide()


        binding.rv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (abs(dy) > scrollOffset) {
                    if (dy < 0) {
                        fab.hide()
                    } else {
                        fab.show()
                    }
                }
            }
        })

        binding.page.onRefresh {
            page = index
            val data = getData(page)
            addData(data) {
                data.isNotEmpty()
            }

        }.autoRefresh()

    }

    private fun refresh() {
        clearCache()
        PageRefreshLayout.startIndex = 1
        binding.page.autoRefresh()
    }

    private fun clearCache() {
        activity?.apply {
            getSharedPreferences(routeName, Context.MODE_PRIVATE).edit().clear().apply()
        }
    }

    fun getData(page: Int): List<LiveBean> {
        activity?.apply {
            getSharedPreferences(routeName, Context.MODE_PRIVATE).apply {
                val json = getString("$page", "")
                val l = gson.fromJson<List<LiveBean>>(json, object : TypeToken<MutableList<LiveBean>>() {}.type)
                if (!l.isNullOrEmpty()) {
                    return l
                }
            }
        }

        return mutableListOf()
    }

    fun query(query: String) {
//
//        binding.rv.models =list.filter {
//            val v = it as LiveBean
//            when {
//                query.isBlank() -> true
//                else -> v.gameName.contains(query, true) ||
//                        v.title.contains(query, true) ||
//                        v.name.contains(query, true)
//            }
//
//        }
    }

}