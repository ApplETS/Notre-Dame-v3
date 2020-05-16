package data.api

import kotlinx.coroutines.runBlocking
import org.junit.Test

internal class WhatsNewApiTest {

    @Test
    fun getWhatsNewEn() {
        val whatsNewApi = WhatsNewApi()
        val whatsNewItems = runBlocking { whatsNewApi.getWhatsNewEn("1.0.0", "7.0.0") }
        assert(!whatsNewItems.isEmpty())
    }

    @Test
    fun getWhatsNewFr() {
        val whatsNewApi = WhatsNewApi()
        val whatsNewItems = runBlocking { whatsNewApi.getWhatsNewFr("1.0.0", "7.2.0") }
        assert(!whatsNewItems.isEmpty())
    }
}