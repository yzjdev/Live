package app.live.droid.utils

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Process
import android.widget.TextView
import app.live.droid.base.BaseActivity
import app.live.droid.extensions.context
import java.io.PrintWriter
import java.io.StringWriter

object CrashUtils : Thread.UncaughtExceptionHandler {


    val defaultHandler = Thread.getDefaultUncaughtExceptionHandler()
    var carshInfo:String = ""

    fun init() {
        Thread.setDefaultUncaughtExceptionHandler(this)
    }

    override fun uncaughtException(t: Thread, e: Throwable) {

        if (!handleException(e)  && defaultHandler!=null){
            defaultHandler.uncaughtException(t,e)
        }else{
           CrashActivity.actionStart(context, carshInfo )
            ActivityUtils.finishAll()
            Process.killProcess(Process.myPid())
            System.exit(0)
        }
    }


    fun handleException(e:Throwable?) = if (e==null) false else {
        carshInfo = getCrashInfo(e)
        true
    }

    fun getCrashInfo(e: Throwable): String {
        val writer = StringWriter()
        val printWriter = PrintWriter(writer)
        e.printStackTrace(printWriter)
        var cause = e.cause
        while (cause != null) {
            cause.printStackTrace(printWriter)
            cause = cause.cause
        }
        printWriter.close()
        return writer.toString()
    }



    class CrashActivity : BaseActivity(){
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            val text = TextView(this)
            setContentView(text)
            val info = intent.getStringExtra("info")
            text.text = info
        }


        companion object {
             fun actionStart(context: Context, info:String){
                 val intent = Intent(context, CrashActivity::class.java).apply {
                     setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                     putExtra("info",info)
                 }
                 context.startActivity(intent)
             }
        }
    }
}