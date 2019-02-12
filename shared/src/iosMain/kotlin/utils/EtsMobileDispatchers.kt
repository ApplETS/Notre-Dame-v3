package utils

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

/**
 * Created by Sonphil on 12-02-19.
 */

actual object EtsMobileDispatchers {
    actual val Default: CoroutineDispatcher = Dispatchers.Default
    actual val IO: CoroutineDispatcher = TODO()
    actual val Main: CoroutineDispatcher = Dispatchers.Main
}