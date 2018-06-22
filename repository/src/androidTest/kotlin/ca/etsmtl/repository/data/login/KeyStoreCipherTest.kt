package ca.etsmtl.repository.data.login

import android.support.test.InstrumentationRegistry
import ca.etsmtl.repository.data.repository.signets.login.KeyStoreUtils
import org.junit.After
import org.junit.Before

/**
 * Created by Sonphil on 22-04-18.
 */

abstract class KeyStoreCipherTest {
    protected val alias = "TestAlias"
    protected lateinit var keystoreUtils: KeyStoreUtils

    @Before
    open fun setUp() {
        keystoreUtils = KeyStoreUtils(InstrumentationRegistry.getContext())
        keystoreUtils.deleteAndroidKeyStoreKeyEntry(alias)
    }

    @After
    fun after() {
        keystoreUtils.deleteAndroidKeyStoreKeyEntry(alias)
    }
}