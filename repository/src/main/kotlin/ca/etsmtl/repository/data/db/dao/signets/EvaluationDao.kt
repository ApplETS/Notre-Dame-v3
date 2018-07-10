package ca.etsmtl.repository.data.db.dao.signets

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import ca.etsmtl.repository.data.db.entity.signets.EvaluationEntity

/**
 * Created by Sonphil on 24-05-18.
 */
@Dao
abstract class EvaluationDao : SignetsDao<EvaluationEntity> {

    @Query("SELECT * FROM evaluationentity")
    abstract fun getAll(): LiveData<List<EvaluationEntity>>

    @Query("SELECT * FROM evaluationentity WHERE coursGroupe LIKE :coursGroupe")
    abstract fun getByCoursGroupe(coursGroupe: String): LiveData<List<EvaluationEntity>>
}