package presentation

import kotlinx.coroutines.CoroutineScope

/**
 * Created by Sonphil on 12-02-19.
 */

actual open class ViewModel {
    actual val vmScope: CoroutineScope
        get() = TODO()
}