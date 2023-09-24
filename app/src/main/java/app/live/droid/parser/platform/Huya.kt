package app.live.droid.parser.platform

import app.live.droid.logic.network.huya.HuyaRepository
import app.live.droid.parser.LiveParser

class Huya : LiveParser() {
    override fun getLives(pageNo: Int) = HuyaRepository.getLives(pageNo)

    override fun getStream(room: String)= HuyaRepository.getStream(room)
}