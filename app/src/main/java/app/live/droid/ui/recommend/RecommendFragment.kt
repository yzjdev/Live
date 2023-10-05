package app.live.droid.ui.recommend

import android.content.Context
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
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
import com.drake.brv.utils.addModels
import com.drake.brv.utils.models
import com.drake.brv.utils.setup
import com.google.gson.reflect.TypeToken
import kotlin.math.abs


class RecommendFragment constructor(private val liveParser: LiveParser?) : BaseFragment<FragmentRecommendBinding, RecommendViewModel>() {

    private val routeName = when (liveParser) {
        is Huya -> "huya"
        is Douyu -> "douyu"
        is Kuaishou -> "kuaishou"
        else -> ""
    }

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


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindRv()
        
        //   model.getLives(1)
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
    fun bindRv() {
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

        val fab =binding.fab

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


        binding.refresh.onRefresh {
            page = index
            model.getLives(page)
            val data = getData(page)
            addData(data) {
                true
            }

        }.autoRefresh()
    }

    fun clearCache() {
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