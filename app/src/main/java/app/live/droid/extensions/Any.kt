package app.live.droid.extensions


import com.google.gson.JsonParser

fun Any.parseObject() = JsonParser.parseString(this.toString()).asJsonObject


fun Any.parseArray() = JsonParser.parseString(this.toString()).asJsonArray


