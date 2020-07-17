package dev.fritz2.binding

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.khronos.webgl.ArrayBuffer
import org.khronos.webgl.ArrayBufferView
import org.w3c.dom.MessageEvent
import org.w3c.dom.WebSocket
import org.w3c.dom.events.Event
import org.w3c.files.Blob

open class WebSocketRemote(
    val url: String,
    val protocols: List<String> = listOf("")
) {
    private val socket: WebSocket

    init {
        try {
            socket = WebSocket(url, protocols)
        } catch (ex: Throwable) {
            console.error("Error initializing websocket!")
            console.error(ex)
            throw ex
        }
    }

    val messages: Flow<MessageEvent> = flow {
        socket.onmessage = {
            suspend {
                this.emit(it)
            }
        }
    }

    val events: Flow<Event> = flow {
        socket.onopen = {
            suspend { emit(it) }
        }
        socket.onclose = {
            suspend { emit(it) }
        }
        socket.onerror = {
            suspend { emit(it) }
        }
    }

    val errors: Flow<Event> = flow {
        socket.onerror = {
            suspend {
                this.emit(it)
            }
        }
    }

    fun close(code: Short = 1000, reason: String = "") = socket.close(code, reason)

    fun send(data: String) = socket.send(data)
    fun send(data: Blob) = socket.send(data)
    fun send(data: ArrayBuffer) = socket.send(data)
    fun send(data: ArrayBufferView) = socket.send(data)
}
