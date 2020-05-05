package presentation

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

/**
 * Created by Sonphil on 12-02-19.
 */

actual open class ViewModel {
    actual val vmScope: CoroutineScope = CoroutineScope(Dispatchers.Main)
}