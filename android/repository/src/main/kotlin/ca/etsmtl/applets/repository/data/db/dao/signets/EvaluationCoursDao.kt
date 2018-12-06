package ca.etsmtl.applets.repository.data.db.dao.signets

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import ca.etsmtl.applets.repository.data.db.entity.signets.EvaluationCoursEntity

/**
 * Created by Sonphil on 05-12-18.
 */
@Dao
abstract class EvaluationCoursDao {
    @Query("SELECT * FROM evaluationcoursentity WHERE session LIKE :sessionAbrege")
    abstract fun getEvaluationCoursBySession(sessionAbrege: String): LiveData<List<EvaluationCoursEntity>>

    @Query("DELETE FROM evaluationcoursentity WHERE session LIKE :sessionAbrege")
    abstract fun deleteBySession(sessionAbrege: String)
}