package app.live.droid.parser.platform

import app.live.droid.logic.network.HuyaRepository
import app.live.droid.parser.LiveParser

class Huya : LiveParser() {
    override fun getLives(page: Int) = HuyaRepository.getLives(page)

    override fun getStream(room: String)= HuyaRepository.getStream(room)
}