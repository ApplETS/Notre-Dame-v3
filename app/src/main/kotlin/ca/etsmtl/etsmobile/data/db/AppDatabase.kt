package ca.etsmtl.etsmobile.data.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import ca.etsmtl.etsmobile.data.db.dao.EtudiantDao
import ca.etsmtl.etsmobile.data.db.dao.ProgrammeDao
import ca.etsmtl.etsmobile.data.model.signets.Etudiant
import ca.etsmtl.etsmobile.data.model.signets.Programme

/**
 * Created by Sonphil on 13-03-18.
 */
@Database(
        entities = [
            (Programme::class),
            (Etudiant::class)
        ],
        version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun programmeDao(): ProgrammeDao
    abstract fun etudiantDao(): EtudiantDao
}