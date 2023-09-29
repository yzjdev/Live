package app.live.droid.parser.platform

import app.live.droid.logic.network.DouyuRepository
import app.live.droid.parser.LiveParser

class Douyu : LiveParser() {
    override fun getLives(page: Int) = DouyuRepository.getLives(page)


    override fun getStream(room: String) = DouyuRepository.getStream(room)



}