package app.live.droid.parser

import androidx.lifecycle.LiveData
import app.live.droid.logic.model.LiveBean
import app.live.droid.logic.model.StreamBean
import java.io.Serializable

abstract class LiveParser : Serializable {

    var name: String? = null
    var className: String? = null

    abstract fun getLives(page: Int): LiveData<Result<List<LiveBean>>>

    abstract fun getStream(room:String): LiveData<Result<StreamBean>>




}