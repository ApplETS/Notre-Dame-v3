package utils

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel

/**
 * A channel that keep its last value
 *
 * Created by Sonphil on 29-07-19.
 */

class LastValueReceiveChannel<T>(private val channel: Channel<T?> = Channel()) : ReceiveChannel<T?> by channel {
    var lastValue: T? = null
        private set

    /**
     * Save [element] into [lastValue] and send [element]
     */
    suspend fun submit(element: T?) {
        lastValue = element

        channel.send(element)
    }
}