package app.live.droid.component

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import app.live.droid.R
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer

class PlayerView : StandardGSYVideoPlayer{
    constructor(context: Context, fullFlag:Boolean): super(context, fullFlag)

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs:AttributeSet?) : super(context,attrs)

    init {
        backButton.setOnClickListener {
            (context as? Activity)?.finish()
        }
    }


    override fun getLayoutId(): Int {
        return R.layout.player_view
    }

    override fun getEnlargeImageRes(): Int {
        return R.drawable.round_fullscreen_24
    }

    override fun getShrinkImageRes(): Int {
        return R.drawable.round_fullscreen_exit_24
    }


    override fun touchSurfaceMoveFullLogic(absDeltaX: Float, absDeltaY: Float) {
        super.touchSurfaceMoveFullLogic(absDeltaX, absDeltaY)
        mChangePosition = false
    }

    override fun updateStartImage() {
        if (mStartButton is ImageView){

            val imageView = mStartButton as ImageView

            if (mCurrentState == CURRENT_STATE_PLAYING) {
                imageView.setImageResource(R.drawable.round_pause_circle_24)
            } else if (mCurrentState == CURRENT_STATE_ERROR) {
                imageView.setImageResource(R.drawable.baseline_error_24)
            } else {
                imageView.setImageResource(R.drawable.round_play_circle_24)
            }

        }

    }

}