package ca.etsmtl.applets.etsmobile.di.shared

import android.content.Context
import ca.etsmtl.applets.shared.db.EtsMobileDb
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

/**
 * Created by Sonphil on 09-02-19.
 */

@Singleton
@Component(
    modules = [
        DbModule::class
    ]
)
interface DbComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance fun context(context: Context): Builder
        @BindsInstance fun fileName(fileName: String): Builder
        fun build(): DbComponent
    }

    fun etsMobileDb(): EtsMobileDb

    companion object {
        fun builder(): Builder = DaggerDbComponent.builder()
    }
}
