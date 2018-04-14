package ca.etsmtl.etsmobile.util

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

/**
 * Created by Sonphil on 11-04-18.
 */

class KeyboardUtils {

    companion object {
        /**
         * Hide the keyboard
         *
         * @param currentFocus currently focused view
         */
        fun hideKeyboard(currentFocus: View) {
            val imm = currentFocus.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus.windowToken, 0)
        }
    }
}