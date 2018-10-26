package ca.etsmtl.applets.etsmobile.presentation.schedule

import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.ViewModel
import ca.etsmtl.applets.etsmobile.presentation.App
import ca.etsmtl.applets.repository.data.model.SignetsUserCredentials
import ca.etsmtl.applets.repository.data.repository.signets.CoursRepository
import javax.inject.Inject

/**
 * Created by mykaelll87 on 2018-10-24
 */
class ScheduleViewModel @Inject constructor(
        private val repository: CoursRepository,//TODO Mettre le bon repository
        private val userCredential: SignetsUserCredentials,
        private val app:App
) : ViewModel(), LifecycleObserver{

}