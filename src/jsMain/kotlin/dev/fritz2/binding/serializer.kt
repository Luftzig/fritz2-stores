package dev.fritz2.binding

interface Serializer<T, S> {
    fun write(item: T): S
    fun read(msg: S): T
}

class JsonSerializer<T> : Serializer<T, String> {
    override fun write(item: T): String {
        TODO("Not yet implemented")
    }

    override fun read(msg: String): T {
        TODO("Not yet implemented")
    }
}
