package ca.etsmtl.applets.repository.data.db.dao.signets

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import android.arch.persistence.room.Transaction
import ca.etsmtl.applets.repository.data.db.entity.signets.SommaireElementsEvaluationEntity

/**
 * Created by Sonphil on 26-05-18.
 */
@Dao
abstract class SommaireElementsEvaluationDao : SignetsDao<SommaireElementsEvaluationEntity> {
    @Query("SELECT * FROM sommaireelementsevaluationentity")
    abstract fun getAll(): LiveData<List<SommaireElementsEvaluationEntity>>

    @Query("SELECT * FROM sommaireelementsevaluationentity WHERE sigleCours LIKE :sigleCours AND session LIKE :session LIMIT 1")
    abstract fun getBySigleCoursAndSession(sigleCours: String, session: String): LiveData<SommaireElementsEvaluationEntity>

    @Query("DELETE FROM sommaireelementsevaluationentity WHERE sigleCours LIKE :sigleCours AND session LIKE :session")
    abstract fun deleteBySigleCoursAndSession(sigleCours: String, session: String)

    @Transaction
    open fun clearAndInsertBySigleCoursAndSession(sigleCours: String, session: String, sommaireElementsEvaluationEntity: SommaireElementsEvaluationEntity) {
        deleteBySigleCoursAndSession(sigleCours, session)
        insert(sommaireElementsEvaluationEntity)
    }
}