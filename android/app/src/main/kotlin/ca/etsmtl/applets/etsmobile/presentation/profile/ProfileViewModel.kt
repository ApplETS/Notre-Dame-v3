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
import ca.etsmtl.applets.etsmobile.domain.FetchProgrammesUseCase
import ca.etsmtl.applets.etsmobile.presentation.App
import ca.etsmtl.applets.etsmobile.util.Event
import ca.etsmtl.applets.etsmobile.util.getGenericErrorMessage
import ca.etsmtl.applets.repository.data.model.Etudiant
import ca.etsmtl.applets.repository.data.model.Programme
import ca.etsmtl.applets.repository.data.model.Resource
import ca.etsmtl.applets.repository.util.zipResourceTo
import javax.inject.Inject

/**
 * Created by Sonphil on 15-03-18.
 */
class ProfileViewModel @Inject constructor(
    private val fetchEtudiantUseCase: FetchEtudiantUseCase,
    private val fetchProgrammesUseCase: FetchProgrammesUseCase,
    private val app: App
) : ViewModel(), LifecycleObserver {

    private val profileMediatorLiveData: MediatorLiveData<Resource<List<ProfileItem<out ProfileAdapter.ProfileViewHolder>>>> = MediatorLiveData()
    val profile: LiveData<List<ProfileItem<out ProfileAdapter.ProfileViewHolder>>> = Transformations.map(profileMediatorLiveData) {
        it.data
    }
    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading
    val errorMessage: LiveData<Event<String?>> = Transformations.map(profileMediatorLiveData) {
        it.getGenericErrorMessage(app)
    }
    private var etudiantProgrammesPair: LiveData<Resource<Pair<Etudiant?, List<Programme>?>>>? = null

    private fun load() {
        etudiantProgrammesPair = (fetchEtudiantUseCase { true } zipResourceTo fetchProgrammesUseCase()).apply {
            profileMediatorLiveData.addSource(this) { res ->
                _loading.value = res.status == Resource.Status.LOADING

                val sections = mutableListOf<ProfileItem<out ProfileAdapter.ProfileViewHolder>>()
                val etudiant = res.data?.first
                val programmes = res.data?.second

                if (etudiant != null && programmes != null) {
                    etudiant.addToSections(sections)
                    programmes.asReversed().forEach { it.addToSections(sections) }
                }

                profileMediatorLiveData.value = res.copyStatusAndMessage(sections)
            }
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun refresh() {
        etudiantProgrammesPair?.let { profileMediatorLiveData.removeSource(it) }
        etudiantProgrammesPair = null
        load()
    }

    private fun Etudiant.addToSections(sections: MutableList<ProfileItem<out ProfileAdapter.ProfileViewHolder>>) {
        with (sections) {
            add(ProfileHeaderItem(app.getString(R.string.title_student_status_profile)))
            add(ProfileValueItem(app.getString(R.string.label_balance_profile), soldeTotal))
            add(ProfileHeaderItem(app.getString(R.string.title_personal_information_profile)))
            add(ProfileValueItem(app.getString(R.string.label_first_name_profile), prenom))
            add(ProfileValueItem(app.getString(R.string.label_last_name_profile), nom))
            add(ProfileValueItem(app.getString(R.string.label_permanent_code_profile), codePerm))
        }
    }

    private fun Programme.addToSections(sections: MutableList<ProfileItem<out ProfileAdapter.ProfileViewHolder>>) {
        with (sections) {
            add(ProfileHeaderItem(libelle))
            add(ProfileValueItem(app.getString(R.string.label_code_program_profile), code))
            add(ProfileValueItem(app.getString(R.string.label_average_program_profile), moyenne))
            add(ProfileValueItem(app.getString(R.string.label_number_accumulated_credits_program_profile), nbCreditsCompletes.toString()))
            add(ProfileValueItem(app.getString(R.string.label_number_registered_credits_program_profile), nbCreditsInscrits.toString()))
            add(ProfileValueItem(app.getString(R.string.label_number_completed_courses_program_profile), nbCrsReussis.toString()))
            add(ProfileValueItem(app.getString(R.string.label_number_failed_courses_program_profile), nbCrsEchoues.toString()))
            add(ProfileValueItem(app.getString(R.string.label_number_equivalent_courses_program_profile), nbEquivalences.toString()))
            add(ProfileValueItem(app.getString(R.string.label_status_program_profile), statut))
        }
    }
}