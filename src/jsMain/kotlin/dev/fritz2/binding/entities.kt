package dev.fritz2.binding

typealias IdProvider<T, I> = (T) -> I

interface QueryStore<T, I, Q> : EntityStore<T, I> {
    val query: Handler<Q>
}

interface EntityStore<T, I> {
    val load: Handler<I>
    val saveOrUpdate: Handler<T>
    val delete: Handler<I>
}
