package app.live.droid.parser

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import java.lang.reflect.Type

class LiveTypeAdapter : JsonSerializer<LiveParser>, JsonDeserializer<LiveParser> {
    override fun serialize(
        src: LiveParser?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        val obj = JsonObject()
        obj.addProperty("className", src!!.javaClass.name)
        obj.addProperty("name", src.name)
        return obj
    }

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): LiveParser {
        val obj = json!!.asJsonObject
        val className = obj.get("className").asString
        val clazz = Class.forName(className)
        return context!!.deserialize(json, clazz)
    }

}
