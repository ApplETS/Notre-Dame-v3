package ca.etsmtl.applets.etsmobile.domain

import androidx.lifecycle.LiveData
import ca.etsmtl.applets.etsmobile.R
import ca.etsmtl.applets.etsmobile.presentation.App
import com.shopify.livedataktx.map
import com.shopify.livedataktx.nonNull
import model.Resource
import model.Seance
import java.util.Calendar
import javax.inject.Inject

/**
Created by mykaelll87 on 15/02/19
 */
class FetchTodaySeancesUseCase @Inject constructor(
    private val fetchCurrentSessionSeancesUseCase: FetchCurrentSessionSeancesUseCase,
    private val app: App
) {
    operator fun invoke(): LiveData<Resource<List<Seance>>> = fetchCurrentSessionSeancesUseCase()
        .nonNull()
        .map { res ->
            val seances = res.data.filterToday()

            if (res.status == Resource.Status.ERROR) {
                Resource.error(res.message ?: app.getString(R.string.error), seances)
            } else {
                res.copyStatusAndMessage(seances)
            }
        }

    private fun List<Seance>?.filterToday(): List<Seance> = this?.let {
        val start = Calendar.getInstance()
        start.set(Calendar.HOUR_OF_DAY, 0)
        start.set(Calendar.MINUTE, 0)
        start.set(Calendar.SECOND, 0)
        val end = start.clone() as Calendar
        end.add(Calendar.DAY_OF_YEAR, 1)

        it.filter {
            it.dateDebut.timeInMilliseconds in start.timeInMillis..end.timeInMillis
        }
    }.orEmpty()
}