package ca.etsmtl.applets.etsmobile.presentation.whatsnew

class WhatsNewObject(title: String, description: String, version: String) {
    private var mTitle:String = title
    private var mInfo:String = description
    private var mVersion:String = version

    /**
     * Gets the title of the WhatsNew.
     *
     * @return The title of the WhatsNew.
     */
    fun getTitle(): String? {
        return mTitle
    }

    /**
     * Gets the info about the WhatsNew.
     *
     * @return The info about the WhatsNew.
     */
    fun getInfo(): String? {
        return mInfo
    }

    /**
     * Gets the version about the WhatsNew.
     *
     * @return The version about the WhatsNew.
     */
    fun getVersion(): String? {
        return mVersion
    }
}