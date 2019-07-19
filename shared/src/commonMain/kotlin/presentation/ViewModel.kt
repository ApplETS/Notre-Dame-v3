package presentation

import kotlinx.coroutines.CoroutineScope

/**
 * Created by Sonphil on 12-02-19.
 */

expect open class ViewModel() {
    val viewModelScope: CoroutineScope
}