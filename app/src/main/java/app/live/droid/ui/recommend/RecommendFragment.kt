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
import app.live.droid.extensions.isFastClick
import app.live.droid.extensions.loadUrl
import app.live.droid.extensions.toast
import app.live.droid.logic.model.LiveBean
import app.live.droid.parser.LiveParser
import app.live.droid.parser.platform.Douyu
import app.live.droid.parser.platform.Huya
import app.live.droid.parser.platform.Kuaishou
import app.live.droid.ui.player.PlayerActivity
import com.drake.brv.utils.models
import com.drake.brv.utils.setup
import com.google.gson.Gson
import kotlin.math.abs


class RecommendFragment constructor(private val liveParser: LiveParser) : BaseFragment<FragmentRecommendBinding, RecommendViewModel>(true) {

    private val routeName = when (liveParser) {
        is Huya -> "huya"
        is Douyu -> "douyu"
        is Kuaishou -> "kuaishou"
        else -> ""
    }

    val scrollOffset = 4

    private var flagForceRefresh = false
    private var page = 1

    private val mapName get() = "${routeName}_$page"

    val map = mutableMapOf<LiveParser, String>()

    override fun createCustomViewModelIfNeed() = RecommendViewModelFactory(liveParser)

    override fun createViewModelClass() = RecommendViewModel::class.java

    override fun createViewBinding(inflater: LayoutInflater, container: ViewGroup?) = FragmentRecommendBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getLives(1)
        viewModel.liveLiveData.observe(viewLifecycleOwner, Observer { result ->
            val lives = result.getOrNull()
            if (lives != null) {
                if (flagForceRefresh) {
                    flagForceRefresh = false
                    binding.page.showContent()
                    viewModel.liveListMap.clear()
                }
                viewModel.liveListMap[mapName] = lives
                //刷新列表数据
                binding.rv.models = getData()

                //更新搜索框提示
                val hint = "正在直播 ${binding.rv.models?.size}"
                binding.search.hint = hint
                map[liveParser] = hint

                //保存至本地
                val json = Gson().toJson(lives)
                activity?.apply {
                    val editor = getSharedPreferences(routeName, Context.MODE_PRIVATE).edit()
                    editor.putString("$page", json)
                    editor.apply()
                }
            }
        })


        binding.pageGoto.setOnClickListener {
            if (!isFastClick()) {
                page++
                viewModel.getLives(page)
            } else {
                "点这么快干嘛！！".toast()
            }

        }

        binding.page.onRefresh {
            page = index
            viewModel.getLives(page)
            addData(getData()){
                true
            }
        }.autoRefresh()

        binding.forceRefresh.setOnClickListener {
            flagForceRefresh = true
            binding.page.showLoading()
            binding.rv.scrollToPosition(0)
            binding.fabTop.hide()
            requireActivity().getSharedPreferences(routeName, Context.MODE_PRIVATE).edit().clear().apply()
            page = 1
            viewModel.getLives(page)

        }
        binding.search.doAfterTextChanged {
            val s = it.toString()
            query(s)
        }

        val fab = binding.fabTop
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
                    room.text = "${modelPosition+1} ${data.name}"
                    title.text = data.title
                    game.text = data.gameName
                    avatar.loadUrl(data.avatar)
                    preview.loadUrl(data.coverUrl)
                    num.text = data.num
                }

                itemView.setOnClickListener { PlayerActivity.actionStart(requireContext(), liveParser, data) }
            }
        }


    }

    fun getData() = viewModel.liveListMap.run {
        val list = ArrayList<LiveBean>()
        forEach { (_, u) -> list.addAll(u) }
        list
    }

    fun getData(page: Int) = viewModel.liveListMap["${routeName}_$page"]
    fun query(s: String) {
        getData().apply {
            binding.rv.models = this.filter { v ->
                when {
                    s.isBlank() -> true
                    else -> v.gameName.contains(s, true) ||
                            v.title.contains(s, true) ||
                            v.name.contains(s, true)
                }
            }
        }
    }

}