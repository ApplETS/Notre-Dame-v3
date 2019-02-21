package utils

import kotlinx.coroutines.CoroutineDispatcher

/**
 * Created by Sonphil on 12-02-19.
 */

expect object EtsMobileDispatchers {
    val Default: CoroutineDispatcher
    val IO: CoroutineDispatcher
    val Main: CoroutineDispatcher
}