package data.securepreferences

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.net.Uri
import data.securepreferences.utils.CipherUtils
import data.securepreferences.utils.KeyStoreUtils

/**
 * [ContentProvider] used to provide the [Preferences] instance
 *
 * Created by Sonphil on 18-05-19.
 */

internal class PreferencesProvider : ContentProvider() {
    companion object {
        private lateinit var applicationContext: Context
        internal fun preferences(): SharedPreferencesWrapper {
            val shredPrefs = applicationContext.getSharedPreferences(
                "etsmobile_secure_prefs",
                Context.MODE_PRIVATE
            )

            return SharedPreferencesWrapper(
                shredPrefs,
                CipherUtils(),
                KeyStoreUtils(applicationContext)
            )
        }
    }

    override fun onCreate(): Boolean {
        applicationContext = context!!.applicationContext

        return false
    }

    override fun query(
        uri: Uri,
        projection: Array<String>?,
        selection: String?,
        selectionArgs: Array<String>?,
        sortOrder: String?
    ): Cursor? = null

    override fun getType(uri: Uri): String? = null
    override fun insert(uri: Uri, values: ContentValues?): Uri? = null
    override fun delete(
        uri: Uri,
        selection: String?,
        selectionArgs: Array<String>?
    ): Int = 0

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<String>?
    ): Int = 0
}