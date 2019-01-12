package ca.etsmtl.applets.etsmobile.presentation.grades

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ViewModel
import ca.etsmtl.applets.etsmobile.R
import ca.etsmtl.applets.etsmobile.domain.FetchGradesCoursesUseCase
import ca.etsmtl.applets.etsmobile.presentation.App
import ca.etsmtl.applets.etsmobile.util.Event
import ca.etsmtl.applets.etsmobile.util.RefreshableLiveData
import ca.etsmtl.applets.etsmobile.util.getGenericErrorMessage
import ca.etsmtl.applets.repository.data.model.Cours
import ca.etsmtl.applets.repository.data.model.Resource
import com.shopify.livedataktx.map
import com.shopify.livedataktx.nonNull
import javax.inject.Inject

/**
 * Created by Sonphil on 12-08-18.
 */

class GradesViewModel @Inject constructor(
    private val fetchGradesCoursesUseCase: FetchGradesCoursesUseCase,
    private val app: App
) : ViewModel(), LifecycleObserver {
    private var coursRes = object : RefreshableLiveData<Resource<Map<String, List<Cours>>>>() {
        override fun updateSource(): LiveData<Resource<Map<String, List<Cours>>>> {
            return fetchGradesCoursesUseCase()
        }
    }
    val errorMessage: LiveData<Event<String?>> = coursRes
        .nonNull()
        .map {
            it.getGenericErrorMessage(app)
        }

    /** The courses grouped by session **/
    val cours: LiveData<Map<String, List<Cours>>> = coursRes
        .nonNull()
        .map { res ->
            res.data?.mapValues {
                it.value.map { course ->
                    course.adjustCote()
                }
            }
        }

    val loading: LiveData<Boolean> = coursRes
        .nonNull()
        .map {
            it.status == Resource.Status.LOADING
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

    @VisibleForTesting
    fun Cours.adjustCote() = copy(cote = when {
        !cote.isNullOrEmpty() -> cote!!
        !noteSur100.isNullOrEmpty() -> {
            String.format(
                app.getString(R.string.text_grade_in_percentage),
                noteSur100
            )
        }
        else -> app.getString(R.string.abbreviation_not_available)
    })

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun refresh() = coursRes.refresh()
}