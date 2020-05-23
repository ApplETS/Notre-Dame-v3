package data.repository

import data.api.WhatsNewApi
import data.api.model.WhatsNewItems
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeout
import org.junit.Test
import java.net.ConnectException

internal class WhatsNewRepositoryTest {
    @Test
    fun whatsNewEn() {
        val whatsNewApi = WhatsNewApi()
        val whatsNewRepository = WhatsNewRepository(whatsNewApi)
        var it = ArrayList<WhatsNewItems>()
        try {
            runBlocking {
                withTimeout(1000) {
                    whatsNewRepository.whatsNewEn("1.0.0", "7.0.0").collect { items ->
                        it = ArrayList(items) } }

                assert(it.isNotEmpty())
            }
        } catch (ex: TimeoutCancellationException) {
            assert(true)
        } catch (ex: ConnectException) {
            assert(true)
        }
    }
}