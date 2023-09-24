package app.live.droid.utils

import android.content.Context
import java.io.BufferedReader
import java.io.InputStreamReader

object FileUtils {

    fun readAssetsFile(context:Context, fileName:String):String{
        val reader = BufferedReader(InputStreamReader(context.assets.open(fileName)))
        val cbuf = CharArray(1024)
        var len = 0
        val sb = StringBuilder()
        reader.use {
            while (it.read(cbuf).also { len = it } != -1) {
                sb.appendRange(cbuf, 0, len)
            }
        }
        return sb.toString()
    }

}