package ca.etsmtl.applets.repository.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ca.etsmtl.applets.repository.data.db.dao.signets.ActiviteDao
import ca.etsmtl.applets.repository.data.db.dao.signets.CoursDao
import ca.etsmtl.applets.repository.data.db.dao.signets.EnseignantDao
import ca.etsmtl.applets.repository.data.db.dao.signets.EtudiantDao
import ca.etsmtl.applets.repository.data.db.dao.signets.EvaluationCoursDao
import ca.etsmtl.applets.repository.data.db.dao.signets.EvaluationDao
import ca.etsmtl.applets.repository.data.db.dao.signets.HoraireExamenFinalDao
import ca.etsmtl.applets.repository.data.db.dao.signets.JourRemplaceDao
import ca.etsmtl.applets.repository.data.db.dao.signets.ProgrammeDao
import ca.etsmtl.applets.repository.data.db.dao.signets.SeanceDao
import ca.etsmtl.applets.repository.data.db.dao.signets.SessionDao
import ca.etsmtl.applets.repository.data.db.dao.signets.SommaireElementsEvaluationDao
import ca.etsmtl.applets.repository.data.db.entity.signets.ActiviteEntity
import ca.etsmtl.applets.repository.data.db.entity.signets.CoursEntity
import ca.etsmtl.applets.repository.data.db.entity.signets.EnseignantEntity
import ca.etsmtl.applets.repository.data.db.entity.signets.EtudiantEntity
import ca.etsmtl.applets.repository.data.db.entity.signets.EvaluationCoursEntity
import ca.etsmtl.applets.repository.data.db.entity.signets.EvaluationEntity
import ca.etsmtl.applets.repository.data.db.entity.signets.HoraireExamenFinalEntity
import ca.etsmtl.applets.repository.data.db.entity.signets.JourRemplaceEntity
import ca.etsmtl.applets.repository.data.db.entity.signets.ProgrammeEntity
import ca.etsmtl.applets.repository.data.db.entity.signets.SeanceEntity
import ca.etsmtl.applets.repository.data.db.entity.signets.SessionEntity
import ca.etsmtl.applets.repository.data.db.entity.signets.SommaireElementsEvaluationEntity

/**
 * Created by Sonphil on 13-03-18.
 */
@Database(
        entities = [
            ActiviteEntity::class,
            CoursEntity::class,
            EnseignantEntity::class,
            EtudiantEntity::class,
            EvaluationCoursEntity::class,
            EvaluationEntity::class,
            HoraireExamenFinalEntity::class,
            JourRemplaceEntity::class,
            ProgrammeEntity::class,
            SeanceEntity::class,
            SessionEntity::class,
            SommaireElementsEvaluationEntity::class
        ],
        version = 4
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun activiteDao(): ActiviteDao
    abstract fun coursDao(): CoursDao
    abstract fun enseignantDao(): EnseignantDao
    abstract fun etudiantDao(): EtudiantDao
    abstract fun evaluationCoursDao(): EvaluationCoursDao
    abstract fun evaluationDao(): EvaluationDao
    abstract fun horaireExamenFinalDao(): HoraireExamenFinalDao
    abstract fun jourRemplaceDao(): JourRemplaceDao
    abstract fun programmeDao(): ProgrammeDao
    abstract fun seanceDao(): SeanceDao
    abstract fun sessionDao(): SessionDao
    abstract fun sommaireElementsEvaluationDao(): SommaireElementsEvaluationDao
}