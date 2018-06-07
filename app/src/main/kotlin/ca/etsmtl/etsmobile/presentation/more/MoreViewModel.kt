package ca.etsmtl.etsmobile.presentation.more

import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import ca.etsmtl.etsmobile.R
import ca.etsmtl.etsmobile.presentation.App
import ca.etsmtl.repos.data.repository.signets.login.LoginRepository
import javax.inject.Inject

/**
 * Created by Sonphil on 10-05-18.
 */

class MoreViewModel @Inject constructor(
    private val loginRepository: LoginRepository,
    private val app: App
) : AndroidViewModel(app) {
    /**
     * Clears the user's data
     *
     * This function should be called when the user want to log out.
     *
     * @return A [LiveData] containing a [Boolean] who will be true when the process has finished
     */
    fun logout(): LiveData<Boolean> = loginRepository.clearUserData()

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
}