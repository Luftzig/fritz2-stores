package dev.fritz2.binding

typealias IdProvider<T, I> = (T) -> I

abstract class ListStore<T, I>(initialValue: T) : RootStore<T>(initialValue) {
    abstract val load: Handler<Unit>
    abstract val delete: Handler<I>
}

abstract class EntityStore<T, I>(initialValue: T) : RootStore<T>(initialValue) {
    abstract val load: Handler<I>
    abstract val saveOrUpdate: Handler<T>
    abstract val delete: Handler<Unit>
}
