package app.live.droid.extensions

import android.annotation.SuppressLint
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.util.TypedValue
import android.widget.Toast
import app.live.droid.App

@SuppressLint("StaticFieldLeak")
val context = App.context

const val TAG = "LiveLog"

val time get() = System.currentTimeMillis()

fun Any?.toast(context: Context) {
    Handler(Looper.getMainLooper()).post {
        Toast.makeText(context, this.toString(), Toast.LENGTH_SHORT).show()
    }
}

fun Any?.toast() {
    this.toast(context)
}

fun Any?.logDebug(head: String = "") {
    Log.d(TAG, "head: $this")
}


fun Any?.isNull(){
    (this == null).toast()
}

val Any?.dp: Int
    get() = when (this) {
            is Int,
            is Double,
            is Long,
            is Float -> TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this as Float, context.resources.displayMetrics).toInt()
            else -> 0
        }


private const val FAST_CLICK_DELAY_TIME = 1000
private var lastClickTime: Long = 0

fun isFastClick(): Boolean {
    var flag = true
    val currentClickTime = System.currentTimeMillis()
    if (currentClickTime - lastClickTime >= FAST_CLICK_DELAY_TIME) {
        flag = false
    }
    lastClickTime = currentClickTime
    return flag
}

