package dev.fritz2.binding

typealias IdProvider<T, I> = (T) -> I

interface QueryStore<T, I, Q> : EntityStore<T, I> {
    abstract fun load(query: Q): List<T>
}

interface EntityStore<T, I> {
    abstract fun load(id: I): T?
    abstract fun saveOrUpdate(item: T)
    abstract fun delete(id: I)
}
