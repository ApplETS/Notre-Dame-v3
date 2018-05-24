package ca.etsmtl.etsmobile.data.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import ca.etsmtl.etsmobile.data.db.dao.EtudiantDao
import ca.etsmtl.etsmobile.data.db.dao.HoraireExamenFinalDao
import ca.etsmtl.etsmobile.data.db.dao.ProgrammeDao
import ca.etsmtl.etsmobile.data.model.signets.Activite
import ca.etsmtl.etsmobile.data.model.signets.Cours
import ca.etsmtl.etsmobile.data.model.signets.Enseignant
import ca.etsmtl.etsmobile.data.model.signets.Etudiant
import ca.etsmtl.etsmobile.data.model.signets.Evaluation
import ca.etsmtl.etsmobile.data.model.signets.HoraireExamenFinal
import ca.etsmtl.etsmobile.data.model.signets.JourRemplace
import ca.etsmtl.etsmobile.data.model.signets.Programme
import ca.etsmtl.etsmobile.data.model.signets.Session

/**
 * Created by Sonphil on 13-03-18.
 */
@Database(
        entities = [
            Programme::class,
            HoraireExamenFinal::class,
            Cours::class,
            Activite::class,
            Enseignant::class,
            Etudiant::class,
            JourRemplace::class,
            Evaluation::class,
            Session::class
        ],
        version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun programmeDao(): ProgrammeDao
    abstract fun etudiantDao(): EtudiantDao
    abstract fun horaireExamenFinalDao(): HoraireExamenFinalDao
}