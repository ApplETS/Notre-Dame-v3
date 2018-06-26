package ca.etsmtl.repository.data.db.dao.signets

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import ca.etsmtl.repository.data.model.signets.Evaluation

/**
 * Created by Sonphil on 24-05-18.
 */
@Dao
abstract class EvaluationDao : SignetsDao<Evaluation> {

    @Query("SELECT * FROM evaluation")
    abstract fun getAll(): LiveData<List<Evaluation>>

    @Query("SELECT * FROM evaluation WHERE coursGroupe LIKE :coursGroupe")
    abstract fun getByCoursGroupe(coursGroupe: String): LiveData<List<Evaluation>>
}