package app.live.droid.utils

import app.live.droid.extensions.context
import app.live.droid.parser.LiveParser
import app.live.droid.parser.LiveTypeAdapter
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken


object LiveHelper {

    private val typeParser = object : TypeToken<MutableList<LiveParser>>() {}.type

    fun getPlatforms(): List<LiveParser> = GsonBuilder().registerTypeAdapter(LiveParser::class.java, LiveTypeAdapter()).create().fromJson<List<LiveParser>>(FileUtils.readAssetsFile(context, "live.json"), typeParser)

}