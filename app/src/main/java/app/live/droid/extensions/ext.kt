package app.live.droid.extensions

import android.annotation.SuppressLint
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import app.live.droid.App
import com.google.gson.Gson

@SuppressLint("StaticFieldLeak")
val context = App.context
var gson = Gson().create()
val TAG = "LiveLog"



fun Any?.toast(context: Context){
    Handler(Looper.getMainLooper()).post{
        Toast.makeText(context,this.toString(), Toast.LENGTH_SHORT).show()
    }
}

fun Any?.toast(){
   this.toast(context)
}


fun Any?.logDebug(head:String=""){
    Log.d(TAG, "head: $this")
}