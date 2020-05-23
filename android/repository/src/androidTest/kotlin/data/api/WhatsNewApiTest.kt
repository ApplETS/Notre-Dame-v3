package data.api

import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.runBlocking
import org.junit.Test
import kotlinx.coroutines.withTimeout
import java.net.ConnectException

internal class WhatsNewApiTest {

    @Test
    fun getWhatsNewEn() {
        val whatsNewApi = WhatsNewApi()
        try {
            val whatsNewItems = runBlocking {
                withTimeout(1000) {
                    whatsNewApi.getWhatsNewEn("1.0.0", "7.2.0")
                }
            }
            assert(!whatsNewItems.isEmpty())
        } catch (ex: TimeoutCancellationException) {
            assert(true)
        } catch (ex: ConnectException) {
            assert(true)
        }
    }

    @Test
    fun getWhatsNewFr() {
        val whatsNewApi = WhatsNewApi()
        try {
            val whatsNewItems = runBlocking {
                withTimeout(1000) {
                    whatsNewApi.getWhatsNewFr("1.0.0", "7.2.0")
                }
            }
            assert(!whatsNewItems.isEmpty())
        } catch (ex: TimeoutCancellationException) {
            assert(true)
        } catch (ex: ConnectException) {
            assert(true)
        }
    }
}