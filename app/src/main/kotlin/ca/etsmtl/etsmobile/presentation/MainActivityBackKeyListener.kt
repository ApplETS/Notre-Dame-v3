package ca.etsmtl.etsmobile.presentation

/**
 * Interface definition for a callback to be invoked when the back button is pressed in
 * [MainActivity]
 */
interface MainActivityBackKeyListener {

    /**
     * Called when the back button has been pressed
     *
     * @return True if the listener has consumed the event, false otherwise
     */
    fun onBackPressed(): Boolean
}