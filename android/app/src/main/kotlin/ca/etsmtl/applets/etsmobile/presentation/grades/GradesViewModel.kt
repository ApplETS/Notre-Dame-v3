package ca.etsmtl.applets.etsmobile.presentation.grades

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import ca.etsmtl.applets.etsmobile.domain.FetchGradesCoursesGroupedBySessionUseCase
import ca.etsmtl.applets.etsmobile.extension.adjustCoteForDisplay
import ca.etsmtl.applets.etsmobile.extension.getGenericErrorMessage
import ca.etsmtl.applets.etsmobile.presentation.App
import ca.etsmtl.applets.etsmobile.util.Event
import ca.etsmtl.applets.etsmobile.util.RefreshableLiveData
import com.shopify.livedataktx.map
import com.shopify.livedataktx.nonNull
import model.Cours
import model.Resource
import javax.inject.Inject

/**
 * Created by Sonphil on 12-08-18.
 */

class GradesViewModel @Inject constructor(
    private val fetchGradesCoursesGroupedBySessionUseCase: FetchGradesCoursesGroupedBySessionUseCase,
    private val app: App
) : ViewModel(), LifecycleObserver {
    private var coursRes = object : RefreshableLiveData<Resource<Map<String, List<Cours>>>>() {
        override fun updateSource(): LiveData<Resource<Map<String, List<Cours>>>> {
            return fetchGradesCoursesGroupedBySessionUseCase()
        }
    }
    val errorMessage: LiveData<Event<String?>> = coursRes
        .nonNull()
        .map {
            it.getGenericErrorMessage(app)
        }

    /** The courses grouped by session **/
    val cours: LiveData<Map<String, List<Cours>>> = Transformations.map(coursRes) { res ->
        res.data?.mapValues {
            it.value.map { course ->
                course.apply { adjustCoteForDisplay(app) }
            }
        }
    }

    val loading: LiveData<Boolean> = coursRes
        .map {
            it == null || it.status == Resource.Status.LOADING
        }

    /**
     * A [LiveData] containing the value true if the empty view should be shown instead of
     * the list
     */
    val showEmptyView: LiveData<Boolean> = coursRes
        .nonNull()
        .map {
            (it.status != Resource.Status.LOADING && (it?.data == null || it.data?.isEmpty() == true))
        }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun refreshIfContentNotLoaded() = coursRes.refreshIfValueIsNull()

    fun refresh() = coursRes.refresh()
}