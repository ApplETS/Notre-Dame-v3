package ca.etsmtl.applets.etsmobile

import androidx.test.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getTargetContext()
        if (BuildConfig.FLAVOR == ("beta")) {
            assertEquals("ca.etsmtl.applets.etsmobile.beta", appContext.packageName)
        } else {
            assertEquals("ca.etsmtl.applets.etsmobile", appContext.packageName)
        }
    }
}
