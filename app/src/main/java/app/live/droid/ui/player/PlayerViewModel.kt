package app.live.droid.ui.player

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import app.live.droid.logic.model.LiveBean
import app.live.droid.parser.LiveParser

class PlayerViewModel(val parser: LiveParser) : ViewModel() {

    val roomLiveData = MutableLiveData<String>()

    val streamLiveData = roomLiveData.switchMap { room -> parser.getStream(room) }


    private val _a = MutableLiveData<LiveBean>()
    val a = _a

    fun setA(liveBean: LiveBean){
        _a.value = liveBean
    }


    fun getStream(room:String){
        roomLiveData.value = room
    }

}