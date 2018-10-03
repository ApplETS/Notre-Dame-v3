package ca.etsmtl.applets.repository.data.db.dao.signets

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import android.arch.persistence.room.Transaction
import ca.etsmtl.applets.repository.data.db.entity.signets.EvaluationEntity

/**
 * Created by Sonphil on 24-05-18.
 */
@Dao
abstract class EvaluationDao : SignetsDao<EvaluationEntity> {

    @Query("SELECT * FROM evaluationentity")
    abstract fun getAll(): LiveData<List<EvaluationEntity>>

    @Query("SELECT * FROM evaluationentity WHERE cours LIKE :cours AND groupe LIKE :groupe AND session LIKE :session")
    abstract fun getByCoursGroupeAndSession(cours: String, groupe: String, session: String): LiveData<List<EvaluationEntity>>

    @Query("DELETE FROM evaluationentity WHERE cours LIKE :cours AND groupe LIKE :groupe AND session LIKE :session")
    abstract fun deleteByCoursGroupeAndSession(cours: String, groupe: String, session: String)

    @Transaction
    open fun clearAndInsertByCoursGroupeAndSession(cours: String, groupe: String, session: String, evaluationEntities: List<EvaluationEntity>) {
        deleteByCoursGroupeAndSession(cours, groupe, session)
        insertAll(evaluationEntities)
    }
}