package dev.fritz2.binding

import org.w3c.dom.get
import kotlin.browser.window

open class LocalStorageEntityStore<T, I>(
    initialData: T,
    val idProvider: IdProvider<T, I>,
    val keyProvider: (I) -> String,
    val serializer: Serializer<T, String>
) :
    RootStore<T>(initialData), EntityStore<T, I> {

    override val load = handle { model, id: I ->
        println("loading $id")
        window.localStorage[keyProvider(id)]?.let(serializer::read) ?: model
    }

    override val saveOrUpdate = handle { model, item: T ->
        println("saving $item")
        window.localStorage.setItem(keyProvider(idProvider(item)), serializer.write(item))
        model
    }

    override val delete = handle { model, id: I ->
        window.localStorage.removeItem(keyProvider(id))
        model
    }
}
