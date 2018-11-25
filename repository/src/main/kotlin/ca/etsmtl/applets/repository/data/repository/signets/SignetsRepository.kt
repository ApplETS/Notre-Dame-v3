package ca.etsmtl.applets.repository.data.repository.signets

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import ca.etsmtl.applets.repository.AppExecutors

/**
 * Created by Sonphil on 17-03-18.
 */
abstract class SignetsRepository(protected val appExecutors: AppExecutors)

/**
 * Transforms a list [LiveData] to a single item [LiveData]. The item is the first element of
 * the list.
 *
 * @param listLiveData The list [LiveData] to transform
 * @return The transformed [LiveData] which will contain the first item. The item can be null
 * if the list was empty.
 */
inline fun <reified T> LiveData<List<T>>.transformToFirstItemLiveData(): LiveData<T> {
    val resultLiveData = MediatorLiveData<T>()
    resultLiveData.addSource(this) {
        resultLiveData.value = when {
            it != null && it.isNotEmpty() -> it[0]
            else -> null
        }

        resultLiveData.removeSource(this)
    }

    return resultLiveData
}