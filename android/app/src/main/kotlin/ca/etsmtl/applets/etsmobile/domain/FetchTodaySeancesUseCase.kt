package ca.etsmtl.applets.etsmobile.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Transformations
import ca.etsmtl.applets.etsmobile.R
import ca.etsmtl.applets.etsmobile.presentation.App
import ca.etsmtl.applets.repository.data.model.Resource
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
    operator fun invoke(): LiveData<Resource<List<Seance>>> = Transformations.switchMap(fetchCurrentSessionSeancesUseCase()) { res ->
        val mediatorLiveData = MediatorLiveData<Resource<List<Seance>>>()

        when (res.status) {
            Resource.Status.LOADING ->
                mediatorLiveData.value = Resource.loading(emptyList())
            Resource.Status.ERROR ->
                mediatorLiveData.value = Resource.error(
                    res.message ?: app.getString(R.string.error), emptyList()
                )
            Resource.Status.SUCCESS -> {
                val start = Calendar.getInstance()
                start.set(Calendar.HOUR_OF_DAY, 0)
                start.set(Calendar.MINUTE, 0)
                start.set(Calendar.SECOND, 0)
                val end = start.clone() as Calendar
                end.add(Calendar.DAY_OF_YEAR, 1)

                val seances = res.data?.filter {
                    it.dateDebut.unixMillis in start.timeInMillis..end.timeInMillis
                }

                mediatorLiveData.value = Resource.success(seances.orEmpty())
            }
        }
        mediatorLiveData
    }
}