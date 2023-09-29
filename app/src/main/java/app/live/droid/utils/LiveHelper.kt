package app.live.droid.utils

import app.live.droid.extensions.context
import app.live.droid.extensions.fromLiveJson
import app.live.droid.extensions.gson

object LiveHelper {

    fun getPlatforms() = gson.fromLiveJson(FileUtils.readAssetsFile(context, "live.json"))

}