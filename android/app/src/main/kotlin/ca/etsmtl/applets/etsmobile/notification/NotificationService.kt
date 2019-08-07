package ca.etsmtl.applets.etsmobile.notification

import android.app.PendingIntent
import android.content.Intent
import ca.etsmtl.applets.etsmobile.presentation.main.MainActivity
import ca.etsmtl.applets.etsmobilenotifications.ETSFcmListenerService
import ca.etsmtl.applets.etsmobilenotifications.MonETSNotification

/**
 * Created by Sonphil on 30-07-19.
 */

class NotificationService : ETSFcmListenerService() {
    override fun saveNewNotification(newNotification: MonETSNotification) {
        // TODO: To be implemented when we would like to display the notifications in app
    }

    override fun notificationClickedIntent(monETSNotification: MonETSNotification): PendingIntent {
        val notifyIntent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        }

        return PendingIntent.getActivity(
            this,
            0,
            notifyIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
    }
}