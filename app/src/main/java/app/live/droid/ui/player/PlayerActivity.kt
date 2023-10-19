package app.live.droid.ui.player

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import androidx.lifecycle.Observer
import app.live.droid.base.BaseActivity
import app.live.droid.components.PlayerView
import app.live.droid.databinding.ActivityPlayerBinding
import app.live.droid.extensions.toast
import app.live.droid.logic.model.LiveBean
import app.live.droid.parser.LiveParser
import com.shuyu.gsyvideoplayer.GSYVideoManager
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack
import com.shuyu.gsyvideoplayer.utils.OrientationUtils


class PlayerActivity : BaseActivity<ActivityPlayerBinding, PlayerViewModel>(true) {


    lateinit var parser: LiveParser
    lateinit var data: LiveBean

    companion object {
        fun actionStart(context: Context, liveParser: LiveParser, liveBean: LiveBean) {
            val intent = Intent(context, PlayerActivity::class.java)
            intent.putExtra("parser", liveParser)
            intent.putExtra("data", liveBean)
            context.startActivity(intent)
        }


    }


    lateinit var currentUrl: String

    lateinit var playerView: PlayerView

    lateinit var orientationUtils: OrientationUtils

    var isPlay = false
    var isPause = true


    override fun createCustomViewModelIfNeed() = PlayerViewModelFatory(parser)

    override fun createViewModelClass() = PlayerViewModel::class.java

    override fun createViewBinding() = ActivityPlayerBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {

        parser = intent.getSerializableExtra("parser") as LiveParser
        data = intent.getSerializableExtra("data") as LiveBean
        super.onCreate(savedInstanceState)
        initPlayer()
        initData()
    }

    private fun initData() {
        viewModel.getStream(data.roomId)
        viewModel.streamLiveData.observe(this, Observer { result ->
            val stream = result.getOrNull() ?: return@Observer
            data.stream = stream
            data.defaultRate = stream.rates[0]
            _onDataChangedListener?.onChanged(this, data)

            if (stream.urls.isNotEmpty()) {
                startPlay(stream.urls[0])
            } else {
                "播放地址出错".toast()
            }
        })

    }

    interface OnDataChangedListener {
        fun onChanged(activity: PlayerActivity, data: LiveBean)
    }

    private var _onDataChangedListener: OnDataChangedListener? = null

    fun setOnDataChangedListener(listener: OnDataChangedListener) {
        _onDataChangedListener = listener
    }

    fun getViewModel() = viewModel

    private fun initPlayer() {
        playerView = binding.playerView
        orientationUtils = OrientationUtils(this, playerView)

        GSYVideoOptionBuilder().apply {
            title = data.title
            setShowDragProgressTextOnSeekBar(false)
            setShowFullAnimation(false)
            setVideoAllCallBack(object : GSYSampleCallBack() {
                override fun onPrepared(url: String?, vararg objects: Any?) {
                    super.onPrepared(url, *objects)
                    isPlay = true
                }

                override fun onQuitFullscreen(url: String?, vararg objects: Any?) {
                    super.onQuitFullscreen(url, *objects)
                    orientationUtils.backToProtVideo()
                }

                override fun onComplete(url: String?, vararg objects: Any?) {
                    super.onComplete(url, *objects)
                    startPlay(url)
                }
            })
        }.build(playerView)

        playerView.fullscreenButton.setOnClickListener {
            orientationUtils.resolveByClick()
            playerView.startWindowFullscreen(this, false, true)
        }
    }

    fun startPlay(url: String?) {
        if (url == null) return
        currentUrl = url
        playerView.setUp(url, false, data.title)
        playerView.startPlayLogic()
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        // ------- ！！！如果不需要旋转屏幕，可以不调用！！！-------
        // 不需要屏幕旋转，还需要设置 setNeedOrientationUtils(false)
        orientationUtils.backToProtVideo()
        if (GSYVideoManager.backFromWindowFull(this)) {
            return
        }
        super.onBackPressed()
    }

    override fun onResume() {
        getCurPlay().onVideoResume()
        super.onResume()
        isPause = false
    }

    override fun onPause() {
        getCurPlay().onVideoPause()
        super.onPause()
        isPause = true
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isPlay) getCurPlay().release()
        orientationUtils.releaseListener()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        if (isPlay && !isPause) playerView.onConfigurationChanged(this, newConfig, orientationUtils, true, true)
        _onDataChangedListener?.onChanged(this, data)
    }

    private fun getCurPlay() = playerView.fullWindowPlayer ?: playerView
}