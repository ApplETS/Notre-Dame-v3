package data.repository

import data.api.WhatsNewApi
import data.api.model.WhatsNewItems
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import org.junit.Test


internal class WhatsNewRepositoryTest
{
    @Test
    fun whatsNewEn()
    {
        val whatsNewApi = WhatsNewApi()
        val whatsNewRepository = WhatsNewRepository(whatsNewApi)
        var it = ArrayList<WhatsNewItems>()
        runBlocking {whatsNewRepository.whatsNewEn("1.0.0", "7.0.0").collect{items ->
            it = ArrayList(items)
            println("Received")}}


        assert(it.isNotEmpty())
    }
}