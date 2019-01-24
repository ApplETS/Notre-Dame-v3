package ca.etsmtl.applets.etsmobile.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider

/**
 * Cette fabrique retourne des instances de ViewModels. Le constructeur a comme paramètre formel
 * Map<Class<out ViewModel>, Provider<ViewModel>>. Cette Map a une Class qui étend ViewModel en tant
 * clé tandis que la valeur est un Provider de ViewModel (un fournisseur de ViewModel).
 *
 * Created by Sonphil on 28-02-18.
 */
class ViewModelFactory @Inject constructor(
    private val creators: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>
) : ViewModelProvider.Factory {

    /**
     * Cette méthode prend en paramètre le type de ViewModel demandé par une Activity ou un
     * Fragment. Par la suite, le Provider est retrouvé en utilisant la Classe fournie en tant que
     * clé. Celui-ci a alors le rôle d'instancier le ViewModel requis.
     *
     * @param modelClass le type de ViewModel requis
     * @return le ViewModel demandé
     */
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        var creator: Provider<out ViewModel>? = creators[modelClass]
        if (creator == null) {
            for ((key, value) in creators) {
                if (modelClass.isAssignableFrom(key)) {
                    creator = value
                    break
                }
            }
        }
        if (creator == null) {
            throw IllegalArgumentException("unknown model class $modelClass")
        }
        try {
            return creator.get() as T
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }
}
