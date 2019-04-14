package ca.etsmtl.applets.etsmobile.extension

import android.content.Context
import ca.etsmtl.applets.etsmobile.R
import ca.etsmtl.applets.etsmobile.util.Event
import model.Resource

/**
 * Created by Sonphil on 03-11-18.
 */

/**
 * Returns an [Event] which emit a "No internet connection" message is the device is offline or
 * a generic error message if this [Resource]'s [Resource.Status] is [Resource.Status.ERROR].
 * Otherwise, it emits the [Resource]'s error message.
 */
fun Resource<*>.getGenericErrorMessage(context: Context): Event<String?> {
    return if (status == Resource.Status.ERROR) {
        when {
            !context.isDeviceConnected() -> {
                Event(context.getString(R.string.error_no_internet_connection))
            }
            else -> Event(context.getString(R.string.error))
        }
    } else {
        Event(message)
    }
}
