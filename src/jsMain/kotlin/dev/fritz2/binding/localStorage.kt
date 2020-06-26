package dev.fritz2.binding

import org.w3c.dom.get
import kotlin.browser.window

open class LocalStorageEntityStore<T, I>(
    initialData: T,
    val idProvider: IdProvider<T, I>,
    val prefix: String = "",
    val serializer: Serializer<T, String>
) :
    RootStore<T>(initialData), EntityStore<T, I> {

    open fun key(id: I): String = id.toString()

    override fun load(id: I): T? = window.localStorage["${prefix}${key(id)}"]?.let(serializer::read)

    override fun saveOrUpdate(item: T) {
        window.localStorage.setItem("${prefix}${key(idProvider(item))}", serializer.write(item))
    }

    override fun delete(id: I) = window.localStorage.removeItem("${prefix}${key(id)}")
}
