package data.repository

import data.api.WhatsNewApi
import data.api.model.WhatsNewItems
import di.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import utils.EtsMobileDispatchers

/**
 * Created by May on 02-05-20.
 */

class WhatsNewRepository @Inject constructor(
        private val api: WhatsNewApi
) {
    suspend fun whatsNewEn(versionFrom: String, versionTo: String): Flow<List<WhatsNewItems>> {
        val flow: Flow<List<WhatsNewItems>> = flow{
            val items =  api.getWhatsNewEn(versionFrom, versionTo)
            emit(items)
        }
        return flow.flowOn(EtsMobileDispatchers.IO)
    }

    suspend fun whatsNewFr(versionFrom: String, versionTo: String): Flow<List<WhatsNewItems>> {
        val flow: Flow<List<WhatsNewItems>> = flow{
            val items =   api.getWhatsNewFr(versionFrom, versionTo)
            emit(items)
        }
        return flow.flowOn(EtsMobileDispatchers.IO)
    }
}