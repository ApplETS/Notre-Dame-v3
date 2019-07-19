package ca.etsmtl.applets.etsmobile.presentation.more

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import ca.etsmtl.applets.etsmobile.BuildConfig
import ca.etsmtl.applets.etsmobile.R
import ca.etsmtl.applets.etsmobile.presentation.App
import ca.etsmtl.applets.etsmobile.util.Event
import ca.etsmtl.applets.repository.data.db.AppDatabase
import com.buglife.sdk.Buglife
import domain.ClearUserDataUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Sonphil on 10-05-18.
 */

class MoreViewModel @Inject constructor(
    private val clearUserDataUseCase: ClearUserDataUseCase,
    /** Used on logout when the user's data needs to be cleared. Will be removed when the db is
     * fully implemented in the shared module
     **/
    private val androidAppDatabase: AppDatabase,
    private val app: App
) : AndroidViewModel(app) {

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading
    private val _displayLogoutConfirmationDialog = MutableLiveData<Boolean>()
    val displayLogoutConfirmationDialog: LiveData<Boolean> = _displayLogoutConfirmationDialog
    private val _displayMessage = MutableLiveData<Event<String>>()
    val displayMessage: LiveData<Event<String>> = _displayMessage
    private val _navigateToLogin = MutableLiveData<Event<Unit>>()
    val navigateToLogin: LiveData<Event<Unit>> = _navigateToLogin
    private val _navigateToAbout = MutableLiveData<Event<Unit>>()
    val navigateToAbout: LiveData<Event<Unit>> = _navigateToAbout
    private val _navigateToOpenSourceLicenses = MutableLiveData<Event<Unit>>()
    val navigateToOpenSourcesLicenses: LiveData<Event<Unit>> = _navigateToOpenSourceLicenses
    private val _navigateToSettings = MutableLiveData<Event<Unit>>()
    val navigateToSettings: LiveData<Event<Unit>> = _navigateToSettings
    private val _navigateToUri = MutableLiveData<Event<Int>>()
    val navigateToUri: LiveData<Event<Int>> = _navigateToUri
    private val _displayBugReportDialog = MutableLiveData<Boolean>()
    val displayBugReportDialog: LiveData<Boolean> = _displayBugReportDialog

    /**
     * Clears the user's data
     *
     * This function should be called when the user want to log out.
     */
    private fun logout() {
        _loading.value = true
        viewModelScope.launch {
            clearUserDataUseCase()

            androidAppDatabase.clearAllTables()

            _loading.postValue(false)

            _displayMessage.postValue(Event(app.getString(R.string.msg_logout_success)))

            _navigateToLogin.postValue(Event(Unit))
        }
    }

    fun itemsList(): List<MoreItem> {
        val items = mutableListOf(
            MoreItem(R.drawable.ic_bug_report_black_24dp, R.string.more_item_label_report_bug) {
                _displayBugReportDialog.value = true
            },
            MoreItem(R.drawable.ic_people_outline_black_24dp, R.string.more_item_label_contributors) {
                _navigateToUri.value = Event(R.string.uri_github_contributors)
            },
            MoreItem(R.drawable.ic_code_black_24dp, R.string.more_item_label_open_source_licenses) {
                _navigateToOpenSourceLicenses.value = Event(Unit)
            }
        )

        @Suppress("ConstantConditionIf")
        if (BuildConfig.FLAVOR == "beta") {
            items.add(MoreItem(R.drawable.ic_help_outline_black_24dp, R.string.more_item_label_beta_faq) {
                _navigateToUri.value = Event(R.string.uri_beta_faq)
            })
        }

        items.add(MoreItem(R.drawable.ic_settings_black_24dp, R.string.more_item_label_settings) {
            _navigateToSettings.value = Event(Unit)
        })

        items.add(MoreItem(R.drawable.ic_exit_to_app_black_24dp, R.string.more_item_label_log_out) {
            _displayLogoutConfirmationDialog.value = true
        })

        return items
    }

    fun reportBugWithScreenshot() {
        _displayBugReportDialog.value = false
        val screenshot = Buglife.captureScreenshot()

        screenshot?.let { Buglife.addAttachment(it) }
        Buglife.showReporter()
    }

    fun reportBugWithVideo() {
        _displayBugReportDialog.value = false

        Buglife.startScreenRecording() // TODO: Fix Buglife's permission flow
    }

    fun clickAbout() {
        _navigateToAbout.value = Event(Unit)
    }

    fun clickLogoutConfirmationDialogButton(confirmedLogout: Boolean) {
        _displayLogoutConfirmationDialog.value = false

        if (confirmedLogout) {
            logout()
        }
    }
}