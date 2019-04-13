package ca.etsmtl.applets.etsmobile.presentation.dashboard.card.todayschedule

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import ca.etsmtl.applets.etsmobile.R
import ca.etsmtl.applets.etsmobile.domain.FetchTodaySeancesUseCase
import ca.etsmtl.applets.etsmobile.extension.getGenericErrorMessage
import ca.etsmtl.applets.etsmobile.presentation.App
import ca.etsmtl.applets.etsmobile.util.Event
import com.shopify.livedataktx.map
import com.shopify.livedataktx.nonNull
import model.Resource
import javax.inject.Inject

/**
Created by mykaelll87 on 15/02/19
 */
class TodayScheduleCardViewModel @Inject constructor(
    fetchTodaySeancesUseCase: FetchTodaySeancesUseCase,
    private val app: App
) : ViewModel() {
    private var seancesRes = fetchTodaySeancesUseCase()

    val errorMessage: LiveData<Event<String?>> = seancesRes.nonNull().map {
        if (app.getString(R.string.msg_api_no_seance) == it.message) {
            Event(null)
        } else
            it.getGenericErrorMessage(app)
    }

    val seances = seancesRes.nonNull().map {
        it.data
    }

    val loading: LiveData<Boolean> = seancesRes.map {
        it == null || it.status == Resource.Status.LOADING
    }

    val showEmptyView: LiveData<Boolean> = seancesRes.nonNull().map {
        it.status != Resource.Status.LOADING && (it.data == null || it.data?.isEmpty() == true)
    }
}