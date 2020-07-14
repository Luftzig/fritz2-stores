package dev.fritz2.binding

import dev.fritz2.remote.body
import dev.fritz2.remote.remote
import kotlinx.coroutines.flow.map

open class RestEntityStore<T, I>(
    initialData: T,
    inline val idProvider: IdProvider<T, I>,
    val baseUrl: String,
    val serializer: Serializer<T, String>,
    val contentType: String = "application/json; charset=utf-8",
    inline val serializeId: (I) -> String = { it.toString() }
) :
    RootStore<T>(initialData), EntityStore<T, I> {

    val remote = remote(baseUrl)

    override val load = apply { id: I ->
        println("loading $id")
        remote.acceptJson()
            .get(serializeId(id))
            .body()
            .map { serializer.read(it) }
    } andThen update

    //FIXME: how to distinguish post und put? via id?
    override val saveOrUpdate = handle { model, item: T ->
        println("saving $item")
        remote.body(serializer.write(item))
            .contentType(contentType)
            .post(serializeId(idProvider(item)))
        model
    }

    override val delete = handle { model, id: I ->
        println("deleting $id")
        remote.delete(serializeId(id))
        model
    }
}
