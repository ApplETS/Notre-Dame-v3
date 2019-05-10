package ca.etsmtl.applets.etsmobile.presentation.more

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import ca.etsmtl.applets.etsmobile.BuildConfig
import ca.etsmtl.applets.etsmobile.R
import ca.etsmtl.applets.etsmobile.domain.ClearUserDataUseCase
import ca.etsmtl.applets.etsmobile.presentation.App
import ca.etsmtl.applets.etsmobile.util.Event
import com.buglife.sdk.Buglife
import javax.inject.Inject

/**
 * Created by Sonphil on 10-05-18.
 */

class MoreViewModel @Inject constructor(
    private val clearUserDataUseCase: ClearUserDataUseCase,
    private val app: App
) : AndroidViewModel(app) {

    private val logoutMediatorLiveData = MediatorLiveData<Boolean>()
    val loading: LiveData<Boolean> = Transformations.map(logoutMediatorLiveData) { it }
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

    /**
     * Clears the user's data
     *
     * This function should be called when the user want to log out.
     */
    private fun logout() {
        with(clearUserDataUseCase()) {
            logoutMediatorLiveData.addSource(this) { finished ->
                finished?.let {
                    logoutMediatorLiveData.value = finished

                    if (finished) {
                        _displayMessage.value = Event(app.getString(R.string.msg_logout_success))

                        logoutMediatorLiveData.removeSource(this)

                        _navigateToLogin.value = Event(Unit)
                    }
                }
            }
        }
    }

    fun itemsList(): List<MoreItem> {
        val items = mutableListOf(
            MoreItem(R.drawable.ic_bug_report_black_24dp, R.string.more_item_label_report_bug) {
                navigateToBuglifeReporter()
            },
            MoreItem(R.drawable.ic_people_outline_black_24dp, R.string.more_item_label_contributors) {
                _navigateToUri.value = Event(R.string.uri_github_contributors)
            },
            MoreItem(R.drawable.ic_code_black_24dp, R.string.more_item_label_open_source_licenses) {
                _navigateToOpenSourceLicenses.value = Event(Unit)
            }
        )

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

    private fun navigateToBuglifeReporter() {
        val screenShot = Buglife.captureScreenshot() // TODO: Let user attach his own file

        screenShot?.let { Buglife.addAttachment(it) }
        Buglife.showReporter()
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