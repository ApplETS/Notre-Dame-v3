package ca.etsmtl.etsmobile.data.db.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import ca.etsmtl.etsmobile.data.model.signets.SommaireElementsEvaluation

/**
 * Created by Sonphil on 26-05-18.
 */
@Dao
abstract class SommaireElementsEvaluationDao : SignetsDao<SommaireElementsEvaluation> {
    @Query("SELECT * FROM sommaireelementsevaluation")
    abstract fun getAll(): LiveData<List<SommaireElementsEvaluation>>
}