package app.live.droid.utils

import android.os.Handler
import android.os.Looper
import android.os.Message
import android.os.SystemClock

class Timer {

    var totalTime: Long = -1
    var intervalTime: Long = 0
    private var remainTime: Long = 0
    private var systemAddTotalTime: Long = 0
    private var listener: TimeListener? = null
    private var curReminTime: Long = 0
    private var isPause = false

    fun start() {
        if (totalTime <= 0 && intervalTime <= 0) {
            throw RuntimeException("you must set the totalTime > 0 or intervalTime >0")
        }
        systemAddTotalTime = SystemClock.elapsedRealtime() + totalTime
        mHandler?.sendEmptyMessage(TIME)
    }

    fun cancel() {
        if (mHandler != null) {
            mHandler?.removeMessages(TIME)
            mHandler = null
        }
    }

    fun pause() {
        if (mHandler != null) {
            mHandler?.removeMessages(TIME)
            isPause = true
            curReminTime = remainTime
        }
    }

    fun resume() {
        if (isPause == true) {
            isPause = false
            totalTime = curReminTime
            start()
        }
    }

    private var mHandler: Handler? = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                TIME -> if (!isPause) soloveTime()
                2 -> isPause = true
                else -> {}
            }
        }
    }

    private fun soloveTime() {
        remainTime = systemAddTotalTime - SystemClock.elapsedRealtime()
        if (remainTime <= 0) {
            if (listener != null) {
                listener!!.onFinish()
                cancel()
            }
        } else if (remainTime < intervalTime) {
            if (null != mHandler) mHandler?.sendEmptyMessageDelayed(TIME, remainTime)
        } else {
            val curSystemTime: Long = SystemClock.elapsedRealtime()
            if (listener != null) {
                listener!!.onInterval(remainTime)
            }
            var delay: Long = curSystemTime + intervalTime - SystemClock.elapsedRealtime()
            while (delay < 0) delay += intervalTime
            if (null != mHandler) {
                mHandler?.sendEmptyMessageDelayed(TIME, delay)
            }
        }
    }

    interface TimeListener {
        fun onFinish()
        fun onInterval(remainTime: Long)
    }

    fun setTimerLiener(listener: TimeListener?) {
        this.listener = listener
    }

    companion object {
        private const val TIME = 1
    }

}

