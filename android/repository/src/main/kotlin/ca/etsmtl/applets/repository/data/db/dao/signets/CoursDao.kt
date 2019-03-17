package ca.etsmtl.applets.repository.data.db.dao.signets

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import ca.etsmtl.applets.repository.data.db.entity.signets.CoursEntity
import ca.etsmtl.applets.repository.data.db.entity.signets.CoursEntityAndNoteSur100

/**
 * Created by Sonphil on 24-05-18.
 */
@Dao
abstract class CoursDao : SignetsDao<CoursEntity> {
    @Query("SELECT * FROM coursentity")
    abstract fun getAll(): LiveData<List<CoursEntity>>

    @Query("SELECT * FROM coursentity WHERE session LIKE :sessionAbrege")
    abstract fun getCoursBySession(sessionAbrege: String): LiveData<List<CoursEntity>>

    @Query("SELECT * from coursentity")
    abstract fun getAllCoursEntityAndNoteSur100(): LiveData<List<CoursEntityAndNoteSur100>>

    @Query("DELETE FROM coursentity")
    abstract fun deleteAll()
}