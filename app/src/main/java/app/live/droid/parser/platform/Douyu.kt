package app.live.droid.parser.platform

import app.live.droid.logic.network.DouyuNetwork
import app.live.droid.parser.LiveParser

class Douyu : LiveParser() {
    override fun getLives(page: Int) = DouyuNetwork.getLives(page)

    override fun getStream(room: String) = DouyuNetwork.getStream(room)


}