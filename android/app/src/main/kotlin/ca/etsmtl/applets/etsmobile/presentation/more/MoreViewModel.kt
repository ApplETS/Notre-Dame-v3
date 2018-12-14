package ca.etsmtl.applets.etsmobile.presentation.more

import android.app.Activity
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import ca.etsmtl.applets.etsmobile.R
import ca.etsmtl.applets.etsmobile.domain.ClearUserDataUseCase
import ca.etsmtl.applets.etsmobile.presentation.App
import ca.etsmtl.applets.etsmobile.presentation.about.AboutActivity
import ca.etsmtl.applets.etsmobile.presentation.login.WelcomeActivity
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

    enum class ItemsIndex {
        ABOUT, LOGOUT
    }

    private val logoutMediatorLiveData by lazy { MediatorLiveData<Boolean>() }
    val loading: LiveData<Boolean> = Transformations.map(logoutMediatorLiveData) { it }
    private val _displayLogoutConfirmationDialog by lazy { MutableLiveData<Boolean>() }
    val displayLogoutConfirmationDialog: LiveData<Boolean> = _displayLogoutConfirmationDialog
    private val _displayMessage by lazy { MutableLiveData<Event<String>>() }
    val displayMessage: LiveData<Event<String>> = _displayMessage
    private val _activityToGoTo by lazy { MutableLiveData<Event<Class<out Activity>>>() }
    val activityToGoTo: LiveData<Event<Class<out Activity>>> = _activityToGoTo

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

                        _activityToGoTo.value = Event(WelcomeActivity::class.java)
                    }
                }
            }
        }
    }

    fun itemsList(): List<MoreItem> {
        return listOf(
            MoreItem(R.drawable.ic_mini_logo_applets, R.string.more_item_label_about_applets) {
                _activityToGoTo.value = Event(AboutActivity::class.java)
            },
            MoreItem(R.drawable.ic_bug_report_black_24dp, R.string.more_item_report_bug) {
                navigateToBuglifeReporter()
            },
            MoreItem(R.drawable.ic_exit_to_app_black_24dp, R.string.more_item_label_log_out) {
                _displayLogoutConfirmationDialog.value = true
            }
        )
    }

    private fun navigateToBuglifeReporter() {
        val screenShot = Buglife.captureScreenshot() // TODO: Let user attach his own file

        screenShot?.let { Buglife.addAttachment(it) }
        Buglife.showReporter()
    }

    fun clickLogoutConfirmationDialogButton(confirmedLogout: Boolean) {
        _displayLogoutConfirmationDialog.value = false

        if (confirmedLogout)
            logout()
    }
}