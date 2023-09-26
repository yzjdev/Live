package app.live.droid.extensions

import android.annotation.SuppressLint
import app.live.droid.App
import com.google.gson.Gson

@SuppressLint("StaticFieldLeak")
val context = App.context
var gson = Gson().create()
val TAG = "LiveLog"
