package ca.etsmtl.applets.etsmobile.di

import androidx.lifecycle.ViewModel
import dagger.MapKey
import kotlin.reflect.KClass

/**
 * Cette classe représente une clé pouvant être utilisée par Dagger 2 afin d'associer une classe
 * de ViewModel à un Provider. Par la suite, Dagger 2 l'injecte dans une Map.
 *
 * Created by Sonphil on 01-03-18.
 */
@Retention(AnnotationRetention.RUNTIME)
@MapKey internal annotation class ViewModelKey(val value: KClass<out ViewModel>)