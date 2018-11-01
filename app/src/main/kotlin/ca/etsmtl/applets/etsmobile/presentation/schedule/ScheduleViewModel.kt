package ca.etsmtl.applets.etsmobile.presentation.schedule

import androidx.lifecycle.*
import ca.etsmtl.applets.etsmobile.R
import ca.etsmtl.applets.etsmobile.domain.FetchCurrentSessionSeancesUseCase
import ca.etsmtl.applets.etsmobile.presentation.App
import ca.etsmtl.applets.etsmobile.util.Event
import ca.etsmtl.applets.etsmobile.util.isDeviceConnected
import ca.etsmtl.applets.repository.data.model.Resource
import ca.etsmtl.applets.repository.data.model.Seance
import ca.etsmtl.applets.repository.data.model.SignetsUserCredentials
import javax.inject.Inject

/**
 * Created by mykaelll87 on 2018-10-24
 */
class ScheduleViewModel @Inject constructor(
    private var userCredentials: SignetsUserCredentials,
    private val fetchCurrentSessionSeancesUseCase: FetchCurrentSessionSeancesUseCase,
    private val app:App
) : ViewModel(), LifecycleObserver{
    private val seancesMediatorLiveData: MediatorLiveData<Resource<List<Seance>>> by lazy {
        MediatorLiveData<Resource<List<Seance>>>()
    }
    private var seancesLiveData: LiveData<Resource<List<Seance>>>? =null

    val errorMessage: LiveData<Event<String?>> by lazy {
        Transformations.map(seancesMediatorLiveData) {
            if (it.status == Resource.Status.ERROR) {
                when {
                    !app.isDeviceConnected() -> {
                        Event(app.getString(R.string.error_no_internet_connection))
                    }
                    else -> Event(app.getString(R.string.error))
                }
            } else {
                Event(it.message)
            }
        }
    }
    val seances: LiveData<List<Seance>> = Transformations.map(seancesMediatorLiveData){
        it.data
    }

    fun getLoading(): LiveData<Boolean> = Transformations.map(seancesMediatorLiveData){
        it.status == Resource.Status.LOADING
    }

    fun getShowEmptyView():LiveData<Boolean> = Transformations.map(seancesMediatorLiveData){
        it.status != Resource.Status.LOADING && (it?.data == null || it.data?.isEmpty() == true)
    }

    private fun load(){
        seancesLiveData = fetchCurrentSessionSeancesUseCase(userCredentials).apply {
            seancesMediatorLiveData.addSource(this){
                seancesMediatorLiveData.value = it
            }
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun refresh(){
        seancesLiveData?.let { seancesMediatorLiveData.removeSource(it) }
        seancesLiveData = null
        load()
    }
}