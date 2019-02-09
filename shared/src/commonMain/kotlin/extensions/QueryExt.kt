package extensions

import com.squareup.sqldelight.Query
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.CONFLATED
import kotlinx.coroutines.channels.ReceiveChannel

/**
 * Created by Sonphil on 09-02-19.
 */

/**
 * Convert [Query] to a [ReceiveChannel]
 */
fun <T : Any> Query<T>.asChannel(): ReceiveChannel<Query<T>> {
    // Create a ConflatedChannel
    val channel = Channel<Query<T>>(CONFLATED)
    // Ensure consumers immediately run the query.
    channel.offer(this)

    val listener = object : Query.Listener {
        override fun queryResultsChanged() {
            channel.offer(this@asChannel)
        }
    }

    addListener(listener)
    channel.invokeOnClose {
        removeListener(listener)
    }

    return channel
}