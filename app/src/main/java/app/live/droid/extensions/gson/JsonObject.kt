package app.live.droid.extensions.gson

import com.google.gson.JsonObject

fun JsonObject.getJsonObject(key: String) = get(key).asJsonObject
fun JsonObject.getJsonArray(key: String) = get(key).asJsonArray
fun JsonObject.getString(key:String) = get(key).asString
fun JsonObject.getInt(key: String) = get(key).asInt
fun JsonObject.getLong(key:String) = get(key).asLong
fun JsonObject.getBool(key:String) = get(key).asBoolean
fun JsonObject.getByte(key:String) = get(key).asByte
fun JsonObject.getDouble(key:String) = get(key).asDouble
fun JsonObject.getFloat(key:String) = get(key).asFloat
fun JsonObject.getNumber(key:String) = get(key).asNumber
fun JsonObject.getShort(key:String) = get(key).asShort
fun JsonObject.getBigInteger(key:String) = get(key).asBigInteger
fun JsonObject.getBigDecimal(key:String) = get(key).asBigDecimal
