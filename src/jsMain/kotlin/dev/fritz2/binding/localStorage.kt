package dev.fritz2.binding

abstract class LocalStorageStore<T, I>(initialValue: T, idProvider: IdProvider<T, I>) :
    EntityStore<T, I>(initialValue) {
    override val load: Handler<I>
        get() = TODO("Not yet implemented")
    override val saveOrUpdate: Handler<T>
        get() = TODO("Not yet implemented")
    override val delete: Handler<Unit>
        get() = TODO("Not yet implemented")

    abstract val serializer: Serializer<T, String>
}

class LocalStorageJsonStore<T, I>(initialValue: T, idProvider: IdProvider<T, I>) :
    LocalStorageStore<T, I>(initialValue, idProvider) {
    override val serializer = JsonSerializer<T>()
}