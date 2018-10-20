package ca.etsmtl.applets.repository.di

import android.app.Application
import android.arch.persistence.room.Room
import ca.etsmtl.applets.repository.data.db.AppDatabase
import ca.etsmtl.applets.repository.data.db.dao.signets.ActiviteDao
import ca.etsmtl.applets.repository.data.db.dao.signets.CoursDao
import ca.etsmtl.applets.repository.data.db.dao.signets.EnseignantDao
import ca.etsmtl.applets.repository.data.db.dao.signets.EtudiantDao
import ca.etsmtl.applets.repository.data.db.dao.signets.EvaluationDao
import ca.etsmtl.applets.repository.data.db.dao.signets.HoraireExamenFinalDao
import ca.etsmtl.applets.repository.data.db.dao.signets.JourRemplaceDao
import ca.etsmtl.applets.repository.data.db.dao.signets.ProgrammeDao
import ca.etsmtl.applets.repository.data.db.dao.signets.SeanceDao
import ca.etsmtl.applets.repository.data.db.dao.signets.SessionDao
import ca.etsmtl.applets.repository.data.db.dao.signets.SommaireElementsEvaluationDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by Sonphil on 28-02-18.
 */
@Module
internal open class DatabaseModule {

    companion object {
        val instance = DatabaseModule()
    }

    @Singleton @Provides
    open fun provideDb(app: Application): AppDatabase =
            Room.databaseBuilder(app, AppDatabase::class.java, "etsmobilerepos.db")
                    .fallbackToDestructiveMigration()
                    .build()

    @Singleton @Provides
    fun provideActiviteDao(db: AppDatabase): ActiviteDao = db.activiteDao()

    @Singleton @Provides
    fun provideCoursDao(db: AppDatabase): CoursDao = db.coursDao()

    @Singleton @Provides
    fun provideEnseignantDao(db: AppDatabase): EnseignantDao = db.enseignantDao()

    @Singleton @Provides
    fun provideEtudiantDao(db: AppDatabase): EtudiantDao = db.etudiantDao()

    @Singleton @Provides
    fun provideEvaluationDao(db: AppDatabase): EvaluationDao = db.evaluationDao()

    @Singleton @Provides
    fun provideHoraireExamenFinalDao(db: AppDatabase): HoraireExamenFinalDao = db.horaireExamenFinalDao()

    @Singleton @Provides
    fun provideJourRemplaceDao(db: AppDatabase): JourRemplaceDao = db.jourRemplaceDao()

    @Singleton @Provides
    fun provideProgrammeDao(db: AppDatabase): ProgrammeDao = db.programmeDao()

    @Singleton @Provides
    fun provideSeanceDao(db: AppDatabase): SeanceDao = db.seanceDao()

    @Singleton @Provides
    fun provideSessionDao(db: AppDatabase): SessionDao = db.sessionDao()

    @Singleton @Provides
    fun provideSommaireElementsEvaluationDao(db: AppDatabase): SommaireElementsEvaluationDao = db.sommaireElementsEvaluationDao()
}