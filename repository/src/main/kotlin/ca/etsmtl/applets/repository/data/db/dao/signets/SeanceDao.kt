package ca.etsmtl.applets.repository.data.db.dao.signets

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import android.arch.persistence.room.Transaction
import ca.etsmtl.applets.repository.data.db.entity.signets.SeanceEntity

/**
 * Created by Sonphil on 02-06-18.
 */
@Dao
abstract class SeanceDao : SignetsDao<SeanceEntity> {
    @Query("SELECT * FROM seanceentity")
    abstract fun getAll(): LiveData<List<SeanceEntity>>

    @Query("SELECT * FROM seanceentity WHERE sigleCours LIKE :sigleCours AND session LIKE :session")
    abstract fun getByCoursAndSession(sigleCours: String, session: String): LiveData<List<SeanceEntity>>

    @Query("DELETE FROM seanceentity WHERE sigleCours LIKE :sigleCours  AND session LIKE :session")
    abstract fun deleteByCoursAndSession(sigleCours: String, session: String)

    @Transaction
    open fun clearAndInsertByCoursAndSession(sigleCours: String, session: String, seances: List<SeanceEntity>) {
        deleteByCoursAndSession(sigleCours, session)
        insertAll(seances)
    }
}