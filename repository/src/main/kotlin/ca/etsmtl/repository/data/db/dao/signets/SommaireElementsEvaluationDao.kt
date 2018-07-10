package ca.etsmtl.repository.data.db.dao.signets

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import ca.etsmtl.repository.data.db.entity.signets.SommaireElementsEvaluationEntity

/**
 * Created by Sonphil on 26-05-18.
 */
@Dao
abstract class SommaireElementsEvaluationDao : SignetsDao<SommaireElementsEvaluationEntity> {
    @Query("SELECT * FROM sommaireelementsevaluationentity")
    abstract fun getAll(): LiveData<List<SommaireElementsEvaluationEntity>>
}