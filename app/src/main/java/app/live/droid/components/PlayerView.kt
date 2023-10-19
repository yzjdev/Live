package app.live.droid.components

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.View.OnClickListener
import android.widget.ImageView
import android.widget.TextView
import app.live.droid.R
import app.live.droid.logic.model.LiveBean
import app.live.droid.ui.player.PlayerActivity
import com.github.zawadz88.materialpopupmenu.popupMenu
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer


class PlayerView : StandardGSYVideoPlayer, OnClickListener {

    constructor(context: Context, fullFlag: Boolean) : super(context, fullFlag)

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    lateinit var rateView: TextView
    lateinit var refreshView:ImageView

    private val activity get() = context as PlayerActivity

    private lateinit var data: LiveBean

    init {
        initView()
        activity.setOnDataChangedListener(object : PlayerActivity.OnDataChangedListener {
            override fun onChanged(activity: PlayerActivity, data: LiveBean) {
                this@PlayerView.data = data
                val defaultRate = data.defaultRate
                if (defaultRate==null){
                    rateView.text = data.stream?.rates?.get(0)?.name
                }else{
                    rateView.text = defaultRate.name
                }
            }
        })

        backButton.setOnClickListener(this)
        rateView.setOnClickListener(this)
    }

    private fun initView(){
        rateView = findViewById(R.id.rate)
        refreshView = findViewById(R.id.refresh)
    }

    override fun onClick(v: View?) {
        super.onClick(v)
        when(v){
            backButton -> activity.finish()
            rateView -> setRateList()
            refreshView -> {

            }
        }
    }

    private fun setRateList() {
        val list = data.stream?.rates
        if (list.isNullOrEmpty()) return
        val popupMenu = popupMenu {
            dropdownGravity = if (mIfCurrentIsFullscreen) Gravity.TOP else Gravity.BOTTOM
            section {
                list.forEach {
                    item {
                        label = it.name
                        //icon = R.drawable.abc_ic_menu_copy_mtrl_am_alpha //optional
                        callback = { //optional
                            //  rate  it
                            rateView.text = label
                        }
                    }
                }
            }
        }
        popupMenu.show(context, rateView)
    }

    override fun showWifiDialog() {}

    override fun getLayoutId() = R.layout.player_view

    override fun getEnlargeImageRes() = R.drawable.round_fullscreen_24

    override fun getShrinkImageRes() = R.drawable.round_fullscreen_exit_24

    override fun touchSurfaceMoveFullLogic(absDeltaX: Float, absDeltaY: Float) {
        super.touchSurfaceMoveFullLogic(absDeltaX, absDeltaY)
        mChangePosition = false
    }

    override fun updateStartImage() {
        if (mStartButton is ImageView) {
            val imageView = mStartButton as ImageView
            val id = when (mCurrentState) {
                CURRENT_STATE_PLAYING -> R.drawable.round_pause_circle_24
                CURRENT_STATE_ERROR -> R.drawable.baseline_error_24
                else -> R.drawable.round_play_circle_24
            }
            imageView.setImageResource(id)
        }
    }

}