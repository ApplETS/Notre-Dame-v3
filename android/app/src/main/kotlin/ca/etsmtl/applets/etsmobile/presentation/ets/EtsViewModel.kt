package ca.etsmtl.applets.etsmobile.presentation.ets

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ca.etsmtl.applets.etsmobile.R
import ca.etsmtl.applets.etsmobile.presentation.App
import ca.etsmtl.applets.etsmobile.util.Event
import javax.inject.Inject

/**
 * Created by Sonphil on 09-12-18.
 */

typealias ItemClickHandler = (index: Int) -> Unit

class EtsViewModel @Inject constructor(val app: App) : ViewModel() {
    private val _navigateToSecurity = MutableLiveData<Event<Unit>>()
    val navigateToSecurity: LiveData<Event<Unit>> = _navigateToSecurity

    fun itemsList() = listOf(
        EtsItem(R.drawable.ic_security_white_24dp, app.getString(R.string.title_security)) {
            _navigateToSecurity.value = Event(Unit)
        }
    )
}