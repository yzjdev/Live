package app.live.droid.parser.platform

import app.live.droid.logic.network.KuaishouNetwork
import app.live.droid.parser.LiveParser

class Kuaishou : LiveParser() {
    override fun getLives(pageNo: Int) = KuaishouNetwork.getLives(pageNo)

    override fun getStream(room: String) = KuaishouNetwork.getStream(room)
}