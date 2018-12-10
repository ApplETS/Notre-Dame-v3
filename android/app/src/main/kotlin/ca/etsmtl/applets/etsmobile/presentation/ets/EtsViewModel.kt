package ca.etsmtl.applets.etsmobile.presentation.ets

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ca.etsmtl.applets.etsmobile.R
import ca.etsmtl.applets.etsmobile.util.Event
import javax.inject.Inject

/**
 * Created by Sonphil on 09-12-18.
 */

class EtsViewModel @Inject constructor() : ViewModel() {
    private val _navigateToSecurity = MutableLiveData<Event<Unit>>()
    val navigateToSecurity: LiveData<Event<Unit>> = _navigateToSecurity
    private val _navigateToMonEts = MutableLiveData<Event<Unit>>()
    val navigateToMonEts: LiveData<Event<Unit>> = _navigateToMonEts
    private val _navigateToBibliotech = MutableLiveData<Event<Unit>>()
    val navigateToBibliotech: LiveData<Event<Unit>> = _navigateToBibliotech

    fun itemsList() = listOf(
        EtsItem(R.drawable.ic_security_white_24dp, R.string.title_security) {
            _navigateToSecurity.value = Event(Unit)
        },
        EtsItem(R.drawable.ic_monets, null) {
            _navigateToMonEts.value = Event(Unit)
        },
        EtsItem(R.drawable.ic_book_white_24dp, R.string.title_bibliotech) {
            _navigateToBibliotech.value = Event(Unit)
        }
    )
}