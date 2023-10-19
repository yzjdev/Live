package app.live.droid.ui.player

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import app.live.droid.parser.LiveParser

class PlayerViewModel(val parser: LiveParser) : ViewModel() {

    val roomLiveData = MutableLiveData<String>()

    val streamLiveData = roomLiveData.switchMap { room -> parser.getStream(room) }

    fun getStream(room:String){
        roomLiveData.value = room
    }

}