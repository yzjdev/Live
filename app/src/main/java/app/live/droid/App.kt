package app.live.droid

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.shuyu.gsyvideoplayer.player.PlayerFactory
import com.shuyu.gsyvideoplayer.utils.GSYVideoType
import tv.danmaku.ijk.media.exo2.Exo2PlayerManager

class App :Application() {
    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context

    }


    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        PlayerFactory.setPlayManager(Exo2PlayerManager::class.java)
        GSYVideoType.setShowType(GSYVideoType.SCREEN_TYPE_16_9)
    }
}