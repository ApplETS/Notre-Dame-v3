package presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope

/**
 * Created by Sonphil on 12-02-19.
 */

actual open class ViewModel : ViewModel() {
    actual val scope: CoroutineScope = viewModelScope
}