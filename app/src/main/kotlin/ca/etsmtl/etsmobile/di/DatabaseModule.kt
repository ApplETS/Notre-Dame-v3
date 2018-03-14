package ca.etsmtl.etsmobile.di

import android.app.Application
import android.arch.persistence.room.Room
import ca.etsmtl.etsmobile.data.db.AppDatabase
import ca.etsmtl.etsmobile.data.db.dao.EtudiantDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by Sonphil on 28-02-18.
 */
@Module
open class DatabaseModule {

    companion object {
        val instance = DatabaseModule()
    }

    @Singleton
    @Provides
    open fun provideDb(app: Application): AppDatabase =
            Room.databaseBuilder(app, AppDatabase::class.java, "etsmobile.db")
                    .fallbackToDestructiveMigration()
                    .build()

    @Singleton @Provides
    fun provideInfoEtudiantDao(db: AppDatabase): EtudiantDao = db.etudiantDao()
}