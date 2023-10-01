package app.live.droid.parser.platform

import app.live.droid.logic.network.HuyaNetwork
import app.live.droid.parser.LiveParser

class Huya : LiveParser() {
    override fun getLives(page: Int) = HuyaNetwork.getLives(page)

    override fun getStream(room: String)= HuyaNetwork.getStream(room)
}