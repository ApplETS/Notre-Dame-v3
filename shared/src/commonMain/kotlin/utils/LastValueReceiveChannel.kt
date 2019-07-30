package utils

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel

/**
 * Created by Sonphil on 29-07-19.
 */

class LastValueReceiveChannel<T>(private val channel: Channel<T>): ReceiveChannel<T> by channel {
    var lastValue: T? = null
        private set

    suspend fun submit(element: T) {
        lastValue = element

        channel.send(element)
    }
}