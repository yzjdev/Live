package app.live.droid.parser.platform

import app.live.droid.logic.network.KuaishouRepository
import app.live.droid.parser.LiveParser

class Kuaishou : LiveParser() {
    override fun getLives(pageNo: Int) = KuaishouRepository.getLives(pageNo)

    override fun getStream(room: String) = KuaishouRepository.getStream(room)
}