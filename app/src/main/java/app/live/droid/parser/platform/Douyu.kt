package app.live.droid.parser.platform

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import app.live.droid.extensions.context
import app.live.droid.extensions.gson
import app.live.droid.logic.model.LiveBean
import app.live.droid.logic.network.DouyuNetwork
import app.live.droid.parser.LiveParser
import com.google.gson.reflect.TypeToken

class Douyu : LiveParser() {
    override fun getLives(page: Int): LiveData<Result<List<LiveBean>>> {
        context.getSharedPreferences("douyu", Context.MODE_PRIVATE).apply {
            val json = getString("$page", "")
            val l = gson.fromJson<List<LiveBean>>(json, object : TypeToken<MutableList<LiveBean>>() {}.type)
            if (!l.isNullOrEmpty()) {

                return MutableLiveData<Result<List<LiveBean>>>(Result.success(l))
            }
        }

        return DouyuNetwork.getLives(page)
    }

    override fun getStream(room: String) = DouyuNetwork.getStream(room)



}