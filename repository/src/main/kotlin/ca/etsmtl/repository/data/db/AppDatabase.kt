package ca.etsmtl.repository.data.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import ca.etsmtl.repository.data.db.dao.ActiviteDao
import ca.etsmtl.repository.data.db.dao.CoursDao
import ca.etsmtl.repository.data.db.dao.EnseignantDao
import ca.etsmtl.repository.data.db.dao.EtudiantDao
import ca.etsmtl.repository.data.db.dao.EvaluationDao
import ca.etsmtl.repository.data.db.dao.HoraireExamenFinalDao
import ca.etsmtl.repository.data.db.dao.JourRemplaceDao
import ca.etsmtl.repository.data.db.dao.ProgrammeDao
import ca.etsmtl.repository.data.db.dao.SeanceDao
import ca.etsmtl.repository.data.db.dao.SessionDao
import ca.etsmtl.repository.data.db.dao.SommaireElementsEvaluationDao
import ca.etsmtl.repository.data.model.signets.Activite
import ca.etsmtl.repository.data.model.signets.Cours
import ca.etsmtl.repository.data.model.signets.Enseignant
import ca.etsmtl.repository.data.model.signets.Etudiant
import ca.etsmtl.repository.data.model.signets.Evaluation
import ca.etsmtl.repository.data.model.signets.HoraireExamenFinal
import ca.etsmtl.repository.data.model.signets.JourRemplace
import ca.etsmtl.repository.data.model.signets.Programme
import ca.etsmtl.repository.data.model.signets.Seance
import ca.etsmtl.repository.data.model.signets.Session
import ca.etsmtl.repository.data.model.signets.SommaireElementsEvaluation

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
            Session::class,
            SommaireElementsEvaluation::class,
            Seance::class
        ],
        version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun programmeDao(): ProgrammeDao
    abstract fun horaireExamenFinalDao(): HoraireExamenFinalDao
    abstract fun coursDao(): CoursDao
    abstract fun activiteDao(): ActiviteDao
    abstract fun enseignantDao(): EnseignantDao
    abstract fun etudiantDao(): EtudiantDao
    abstract fun jourRemplaceDao(): JourRemplaceDao
    abstract fun evaluationDao(): EvaluationDao
    abstract fun sessionDao(): SessionDao
    abstract fun sommaireElementsEvaluationDao(): SommaireElementsEvaluationDao
    abstract fun seanceDao(): SeanceDao
}