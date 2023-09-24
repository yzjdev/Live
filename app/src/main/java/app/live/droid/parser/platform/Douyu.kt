package app.live.droid.parser.platform

import app.live.droid.logic.network.douyu.DouyuRepository
import app.live.droid.parser.LiveParser

class Douyu : LiveParser() {
    override fun getLives(pageNo: Int) = DouyuRepository.getLives(pageNo)


    override fun getStream(room: String) = DouyuRepository.getStream(room)



}