package dev.fritz2.binding

import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration

interface Serializer<T, S> {
    fun write(item: T): S
    fun read(msg: S): T
}

interface JsonSerializer<T> : Serializer<T, String> {
}

class KotlinXJsonSerializer<T>(val kserializer: KSerializer<T>) : JsonSerializer<T> {
    companion object {
        val json = Json(JsonConfiguration.Stable)
    }

    override fun write(item: T): String = json.stringify(kserializer, item)
    override fun read(msg: String): T = json.parse(kserializer, msg)
}


