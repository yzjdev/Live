package app.live.droid.extensions

import app.live.droid.parser.LiveParser
import app.live.droid.parser.LiveTypeAdapter
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken

val type = object : TypeToken<MutableList<LiveParser>>() {}.type

fun Gson.create() =
    GsonBuilder().registerTypeAdapter(LiveParser::class.java, LiveTypeAdapter()).create()

fun Gson.toLiveJson(src: Any) = toJson(src, type)

fun Gson.fromLiveJson(json: String) = fromJson<List<LiveParser>>(json, type)