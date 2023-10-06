package app.live.droid

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import app.live.droid.utils.CrashUtils
import com.drake.statelayout.StateConfig
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.shuyu.gsyvideoplayer.player.PlayerFactory
import com.shuyu.gsyvideoplayer.utils.GSYVideoType
import tv.danmaku.ijk.media.exo2.Exo2PlayerManager

class App : Application() {
    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        CrashUtils.init()

        PlayerFactory.setPlayManager(Exo2PlayerManager::class.java)
        GSYVideoType.setShowType(GSYVideoType.SCREEN_TYPE_16_9)

        SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, _ -> ClassicsHeader(context) }
        SmartRefreshLayout.setDefaultRefreshFooterCreator { context, _ -> ClassicsFooter(context) }

        StateConfig.apply {
            loadingLayout = R.layout.loading
        }

    }
}