package app.live.droid.components

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.DefaultLifecycleObserver
import app.live.droid.R
import app.live.droid.logic.model.LiveBean
import app.live.droid.ui.player.PlayerActivity
import com.github.zawadz88.materialpopupmenu.popupMenu
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer


class PlayerView : StandardGSYVideoPlayer, DefaultLifecycleObserver {

    constructor(context: Context, fullFlag: Boolean) : super(context, fullFlag)

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    lateinit var rateView: TextView

    private val activity get() = context as PlayerActivity

    private lateinit var data: LiveBean

    init {
        activity.setOnDataChangedListener(object : PlayerActivity.OnDataChangedListener {
            override fun onChanged(data: LiveBean) {
                this@PlayerView.data = data
                rateView.text = data.stream?.rates?.get(0)?.name
            }
        })

        rateView = findViewById<TextView>(R.id.rate)
        backButton.setOnClickListener { activity.finish() }
        rateView.setOnClickListener {
            setRateList()
        }
    }


    fun setRateList() {
        //  if (data == null) return
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

    override fun startDismissControlViewTimer() {
        //super.startDismissControlViewTimer()
    }

    override fun showWifiDialog() {
        //super.showWifiDialog()
    }

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