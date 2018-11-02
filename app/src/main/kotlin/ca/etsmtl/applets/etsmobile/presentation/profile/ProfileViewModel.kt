package ca.etsmtl.applets.etsmobile.presentation.profile

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import ca.etsmtl.applets.etsmobile.R
import ca.etsmtl.applets.etsmobile.domain.FetchEtudiantUseCase
import ca.etsmtl.applets.etsmobile.presentation.App
import ca.etsmtl.applets.repository.data.model.Etudiant
import ca.etsmtl.applets.repository.data.model.Resource
import com.xwray.groupie.Section
import javax.inject.Inject

/**
 * Created by Sonphil on 15-03-18.
 */
class ProfileViewModel @Inject constructor(
    private val fetchEtudiantUseCase: FetchEtudiantUseCase,
    private val app: App
) : ViewModel(), LifecycleObserver {

    private val profileMediatorLiveData: MediatorLiveData<Resource<List<Section>>> by lazy {
        MediatorLiveData<Resource<List<Section>>>()
    }
    val profile: LiveData<List<Section>> = Transformations.map(profileMediatorLiveData) {
        it.data
    }
    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading
    val errorMessage: LiveData<String> = Transformations.map(profileMediatorLiveData) {
        it.message
    }
    private var etudiantRes: LiveData<Resource<Etudiant>>? = null

    private fun load() {
        etudiantRes = fetchEtudiantUseCase { true }.apply {
            profileMediatorLiveData.addSource<Resource<Etudiant>>(this) { res ->
                val sections = mutableListOf<Section>()
                val etudiant = res.data

                _loading.value = res.status == Resource.Status.LOADING

                if (res.status != Resource.Status.LOADING) {
                    etudiant?.let {
                        sections.add(
                                Section().apply {
                                    setHeader(ProfileHeaderItem(app.getString(R.string.title_student_status_profile)))
                                    add(ProfileItem(app.getString(R.string.label_balance_profile), it.soldeTotal))
                                }
                        )

                        sections.add(
                                Section().apply {
                                    setHeader(ProfileHeaderItem(app.getString(R.string.title_personal_information_profile)))
                                    add(ProfileItem(app.getString(R.string.label_first_name_profile), it.prenom))
                                    add(ProfileItem(app.getString(R.string.label_last_name_profile), it.nom))
                                    add(ProfileItem(app.getString(R.string.label_permanent_code_profile), it.codePerm))
                                }
                        )
                    }

                    profileMediatorLiveData.value = res.copyStatusAndMessage(sections)
                }
            }
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun refresh() {
        etudiantRes?.let { profileMediatorLiveData.removeSource(it) }
        etudiantRes = null
        load()
    }
}