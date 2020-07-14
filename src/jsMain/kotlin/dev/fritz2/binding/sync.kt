package dev.fritz2.binding

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.drop

//FIXME: find better way to get data-flow (special Interface Bindable for Something holding a data-flow)
fun <T, I> EntityStore<T, I>.sync(initialId: I, data: Flow<T>) {
    data.drop(1) handledBy saveOrUpdate
    action(initialId) handledBy load
}