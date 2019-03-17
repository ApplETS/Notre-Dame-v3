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
    private val _navigateToUri = MutableLiveData<Event<Int>>()
    val navigateToUri: LiveData<Event<Int>> = _navigateToUri
    private val _navigateToMoodle = MutableLiveData<Event<Unit>>()
    val navigateToMoodle: LiveData<Event<Unit>> = _navigateToMoodle

    fun itemsList(): List<EtsItem> {
        val items = mutableListOf(
            EtsItem(R.drawable.ic_security_white_24dp, R.string.title_security) {
                _navigateToSecurity.value = Event(Unit)
            },
            EtsItem(R.drawable.ic_monets, null) {
                _navigateToUri.value = Event(R.string.uri_mon_ets)
            },
            EtsItem(R.drawable.ic_book_white_24dp, R.string.title_bibliotech) {
                _navigateToUri.value = Event(R.string.uri_bibliotech)
            },
            EtsItem(R.drawable.ic_newspaper_black_24dp, R.string.title_news) {
                _navigateToUri.value = Event(R.string.uri_news)
            },
            EtsItem(R.drawable.ic_import_contacts_white_24dp, R.string.title_directory) {
                _navigateToUri.value = Event(R.string.uri_directory)
            }
        )

        return items
    }
}