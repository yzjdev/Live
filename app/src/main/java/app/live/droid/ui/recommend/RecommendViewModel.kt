package app.live.droid.ui.recommend

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import app.live.droid.logic.model.LiveBean
import app.live.droid.parser.LiveParser

class RecommendViewModel(parser: LiveParser): ViewModel() {


    private val pageLiveData = MutableLiveData<Int>()

    var liveList = ArrayList<LiveBean>()

    val liveLiveData = pageLiveData.switchMap { pageNo -> parser.getLives(pageNo) }


    fun getLives(pageNo:Int){
        pageLiveData.value = pageNo
    }

}