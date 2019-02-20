package ca.etsmtl.applets.etsmobile.presentation.profile

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ViewModel
import ca.etsmtl.applets.etsmobile.R
import ca.etsmtl.applets.etsmobile.domain.FetchEtudiantUseCase
import ca.etsmtl.applets.etsmobile.domain.FetchProgrammesUseCase
import ca.etsmtl.applets.etsmobile.presentation.App
import ca.etsmtl.applets.etsmobile.util.Event
import ca.etsmtl.applets.etsmobile.util.RefreshableLiveData
import ca.etsmtl.applets.etsmobile.util.getGenericErrorMessage
import model.Resource
import ca.etsmtl.applets.repository.util.zipResourceTo
import com.shopify.livedataktx.map
import com.shopify.livedataktx.nonNull
import model.Etudiant
import model.Programme
import javax.inject.Inject

/**
 * Created by Sonphil on 15-03-18.
 */
class ProfileViewModel @Inject constructor(
    private val fetchEtudiantUseCase: FetchEtudiantUseCase,
    private val fetchProgrammesUseCase: FetchProgrammesUseCase,
    private val app: App
) : ViewModel(), LifecycleObserver {

    private val profileRes = object : RefreshableLiveData<Resource<List<ProfileItem<out ProfileAdapter.ProfileViewHolder>>>>() {
        override fun updateSource(): LiveData<Resource<List<ProfileItem<out ProfileAdapter.ProfileViewHolder>>>> {
            return (fetchEtudiantUseCase { true } zipResourceTo fetchProgrammesUseCase())
                .nonNull()
                .map { res ->
                    val sections = mutableListOf<ProfileItem<out ProfileAdapter.ProfileViewHolder>>()
                    val etudiant = res.data?.first
                    val programmes = res.data?.second

                    if (etudiant != null && programmes != null) {
                        etudiant.addToSections(sections)
                        programmes.asReversed().forEach { it.addToSections(sections) }
                    }

                    res.copyStatusAndMessage(sections.toList())
                }
        }
    }
    val profile: LiveData<List<ProfileItem<out ProfileAdapter.ProfileViewHolder>>> = profileRes
        .nonNull()
        .map {
            it.data
        }
    val loading: LiveData<Boolean> = profileRes
        .map {
            it == null || it.status == Resource.Status.LOADING
        }
    val errorMessage: LiveData<Event<String?>> = profileRes
        .nonNull()
        .map {
            it.getGenericErrorMessage(app)
        }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun refresh() = profileRes.refresh()

    private fun Etudiant.addToSections(sections: MutableList<ProfileItem<out ProfileAdapter.ProfileViewHolder>>) {
        with(sections) {
            add(ProfileHeaderItem(app.getString(R.string.title_student_status_profile)))
            add(ProfileValueItem(app.getString(R.string.label_balance_profile), soldeTotal))
            add(ProfileHeaderItem(app.getString(R.string.title_personal_information_profile)))
            add(ProfileValueItem(app.getString(R.string.label_first_name_profile), prenom))
            add(ProfileValueItem(app.getString(R.string.label_last_name_profile), nom))
            add(ProfileValueItem(app.getString(R.string.label_permanent_code_profile), codePerm))
        }
    }

    private fun Programme.addToSections(sections: MutableList<ProfileItem<out ProfileAdapter.ProfileViewHolder>>) {
        with(sections) {
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