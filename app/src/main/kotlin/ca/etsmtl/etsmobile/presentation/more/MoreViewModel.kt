package ca.etsmtl.etsmobile.presentation.more

import android.app.Activity
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.support.annotation.StringRes
import ca.etsmtl.etsmobile.R
import ca.etsmtl.etsmobile.presentation.App
import ca.etsmtl.etsmobile.presentation.about.AboutActivity
import ca.etsmtl.etsmobile.presentation.login.WelcomeActivity
import ca.etsmtl.etsmobile.util.call
import ca.etsmtl.repository.data.repository.signets.login.LoginRepository
import javax.inject.Inject

/**
 * Created by Sonphil on 10-05-18.
 */

class MoreViewModel @Inject constructor(
    private val loginRepository: LoginRepository,
    private val app: App
) : AndroidViewModel(app) {

    enum class ItemsIndex {
        FAQ, ABOUT, LOGOUT
    }

    private val displayLogoutConfirmationDialog by lazy { MutableLiveData<Void>() }
    private val displayMessage by lazy { MutableLiveData<@StringRes Int>() }
    private val logoutMediatorLiveData by lazy { MediatorLiveData<Boolean>() }
    private val activityToGoTo by lazy { MutableLiveData<Class<out Activity>>() }

    fun getDisplayLogoutDialog(): LiveData<Void> = displayLogoutConfirmationDialog
    fun getDisplayMessage(): LiveData<String> = Transformations.map(displayMessage) {
        app.getString(it)
    }
    fun getActivityToGoTo(): LiveData<Class<out Activity>> = activityToGoTo
    fun getLoading(): LiveData<Boolean> = Transformations.map(logoutMediatorLiveData) { it }

    /**
     * Clears the user's data
     *
     * This function should be called when the user want to log out.
     */
    fun logout() {
        with(loginRepository.clearUserData()) {
            logoutMediatorLiveData.addSource(this) { finished ->
                finished?.let {
                    logoutMediatorLiveData.value = finished

                    if (finished) {
                        displayMessage.value = R.string.msg_logout_success

                        logoutMediatorLiveData.removeSource(this)

                        activityToGoTo.value = WelcomeActivity::class.java
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

    fun selectItem(index: Int) {
        when (index) {
            ItemsIndex.FAQ.ordinal -> TODO()
            ItemsIndex.ABOUT.ordinal -> activityToGoTo.value = AboutActivity::class.java
            ItemsIndex.LOGOUT.ordinal -> displayLogoutConfirmationDialog.call()
        }
    }
}