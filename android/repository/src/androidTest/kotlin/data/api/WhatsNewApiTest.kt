package data.api

import kotlinx.coroutines.runBlocking
import org.junit.Test


internal class WhatsNewApiTest {

    @Test
    fun getWhatsNewEn() {
        val whatsNewApi = WhatsNewApi()
        val whatsnewItems =runBlocking {whatsNewApi.getWhatsNewEn("1.0.0", "7.0.0")}
        assert(!whatsnewItems.isEmpty())
    }

    @Test
    fun getWhatsNewFr() {
        val whatsNewApi = WhatsNewApi()
        val whatsnewItems =runBlocking {whatsNewApi.getWhatsNewFr("1.0.0", "7.2.0")}
        assert(!whatsnewItems.isEmpty())
    }
}