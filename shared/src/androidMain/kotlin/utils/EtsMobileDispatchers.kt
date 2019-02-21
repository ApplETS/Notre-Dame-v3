package utils

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asCoroutineDispatcher
import java.util.concurrent.Executors

/**
 * Created by Sonphil on 12-02-19.
 */

actual object EtsMobileDispatchers {
    actual val Default: CoroutineDispatcher = Dispatchers.Default
    actual val IO: CoroutineDispatcher = Executors.newSingleThreadExecutor().asCoroutineDispatcher()
    actual val Main: CoroutineDispatcher = Dispatchers.Main
}