package ca.etsmtl.applets.etsmobile.presentation.more

import android.app.Activity
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import ca.etsmtl.applets.etsmobile.R
import ca.etsmtl.applets.etsmobile.domain.ClearUserDataUseCase
import ca.etsmtl.applets.etsmobile.presentation.App
import ca.etsmtl.applets.etsmobile.presentation.about.AboutActivity
import ca.etsmtl.applets.etsmobile.presentation.login.WelcomeActivity
import ca.etsmtl.applets.etsmobile.util.Event
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

    private val _displayLogoutConfirmationDialog by lazy { MutableLiveData<Boolean>() }
    private val _displayMessage by lazy { MutableLiveData<Event<String>>() }
    private val logoutMediatorLiveData by lazy { MediatorLiveData<Boolean>() }
    private val _activityToGoTo by lazy { MutableLiveData<Event<Class<out Activity>>>() }
    val loading = Transformations.map(logoutMediatorLiveData) { it }
    val displayLogoutDialog: LiveData<Boolean> = _displayLogoutConfirmationDialog
    val displayMessage: LiveData<Event<String>> = _displayMessage
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
        val moreItems = ArrayList<MoreItem>()
        val icons = app.resources.obtainTypedArray(R.array.more_items_icons)
        val labels = app.resources.getStringArray(R.array.more_items_labels)

        labels.forEachIndexed { index, label ->
            moreItems.add(MoreItem(icons.getResourceId(index,
                    R.drawable.ic_info_white_24dp), label))
        }

        icons.recycle()

        return moreItems
    }

    fun clickLogoutConfirmationDialogButton(confirmedLogout: Boolean) {
        _displayLogoutConfirmationDialog.value = false

        if (confirmedLogout)
            logout()
    }

    fun selectItem(index: Int) {
        when (index) {
            ItemsIndex.ABOUT.ordinal -> _activityToGoTo.value = Event(AboutActivity::class.java)
            ItemsIndex.LOGOUT.ordinal -> _displayLogoutConfirmationDialog.value = true
        }
    }
}