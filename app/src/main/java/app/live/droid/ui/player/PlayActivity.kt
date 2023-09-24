package app.live.droid.ui.player

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import app.live.droid.databinding.ActivityPlayBinding
import app.live.droid.logic.model.LiveBean
import app.live.droid.parser.LiveParser
import com.shuyu.gsyvideoplayer.GSYVideoManager
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack
import com.shuyu.gsyvideoplayer.utils.OrientationUtils
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer
import com.shuyu.gsyvideoplayer.video.base.GSYVideoPlayer


class PlayActivity : AppCompatActivity() {

    companion object {
        fun actionStart(context: Context, liveParser: LiveParser, liveBean: LiveBean) {
            val intent = Intent(context, PlayActivity::class.java)
            intent.putExtra("parser", liveParser)
            intent.putExtra("data", liveBean)
            context.startActivity(intent)
        }
    }

    lateinit var parser: LiveParser
    lateinit var data: LiveBean

    lateinit var viewModel: PlayerViewModel
    lateinit var binding: ActivityPlayBinding

    lateinit var currentUrl: String


    lateinit var playerView: StandardGSYVideoPlayer

    lateinit var orientationUtils: OrientationUtils

    var isPlay = false
    var isPause = true


    @androidx.media3.common.util.UnstableApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayBinding.inflate(layoutInflater)
        setContentView(binding.root)



        parser = intent.getSerializableExtra("parser") as LiveParser
        data = intent.getSerializableExtra("data") as LiveBean

        viewModel = ViewModelProvider(this, PlayerViewModelFatory(parser))[PlayerViewModel::class.java]


        initPlayer()

        viewModel.getStream(data.roomId)
        viewModel.streamLiveData.observe(this, Observer { result ->
            val stream = result.getOrNull()!!

            viewModel.stream = stream
            data.stream = stream
            startPlay(stream.urls[0])
            binding.text.text = stream.toString()
        })

    }

    fun initPlayer() {

        playerView = binding.playerView
        orientationUtils = OrientationUtils(this, playerView)

        GSYVideoOptionBuilder().apply {
            setTitle(data.title)
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
            })

        }.build(playerView)

        playerView.fullscreenButton.setOnClickListener {
            orientationUtils.resolveByClick()
            playerView.startWindowFullscreen(this, false, true)
        }
    }


    fun startPlay(url: String) {
        currentUrl = url
        playerView.setUp(url, false, data.title)
        playerView.startPlayLogic()
    }


    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {


        // ------- ！！！如果不需要旋转屏幕，可以不调用！！！-------
        // 不需要屏幕旋转，还需要设置 setNeedOrientationUtils(false)
        if (orientationUtils != null) {
            orientationUtils.backToProtVideo()
        }

        if (GSYVideoManager.backFromWindowFull(this)) {
            return
        }
        super.onBackPressed()
    }

    override fun onPause() {
        getCurPlay().onVideoPause()
        super.onPause()
        isPause = true
    }

    override fun onResume() {
        getCurPlay().onVideoResume()
        super.onResume()
        isPause = false
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isPlay) {
            getCurPlay().release()
        }
        orientationUtils.releaseListener()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        if (isPlay && !isPause)
            playerView.onConfigurationChanged(this, newConfig, orientationUtils, true, true)
    }

    fun getCurPlay(): GSYVideoPlayer {
        if (playerView.fullWindowPlayer != null) {
            return playerView.fullWindowPlayer
        }
        return playerView
    }
}