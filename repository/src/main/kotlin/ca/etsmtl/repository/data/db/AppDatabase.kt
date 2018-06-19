package ca.etsmtl.repos.data.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import ca.etsmtl.repos.data.db.dao.ActiviteDao
import ca.etsmtl.repos.data.db.dao.CoursDao
import ca.etsmtl.repos.data.db.dao.EnseignantDao
import ca.etsmtl.repos.data.db.dao.EtudiantDao
import ca.etsmtl.repos.data.db.dao.EvaluationDao
import ca.etsmtl.repos.data.db.dao.HoraireExamenFinalDao
import ca.etsmtl.repos.data.db.dao.JourRemplaceDao
import ca.etsmtl.repos.data.db.dao.ProgrammeDao
import ca.etsmtl.repos.data.db.dao.SeanceDao
import ca.etsmtl.repos.data.db.dao.SessionDao
import ca.etsmtl.repos.data.db.dao.SommaireElementsEvaluationDao
import ca.etsmtl.repos.data.model.signets.Activite
import ca.etsmtl.repos.data.model.signets.Cours
import ca.etsmtl.repos.data.model.signets.Enseignant
import ca.etsmtl.repos.data.model.signets.Etudiant
import ca.etsmtl.repos.data.model.signets.Evaluation
import ca.etsmtl.repos.data.model.signets.HoraireExamenFinal
import ca.etsmtl.repos.data.model.signets.JourRemplace
import ca.etsmtl.repos.data.model.signets.Programme
import ca.etsmtl.repos.data.model.signets.Seance
import ca.etsmtl.repos.data.model.signets.Session
import ca.etsmtl.repos.data.model.signets.SommaireElementsEvaluation

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