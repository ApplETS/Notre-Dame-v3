package ca.etsmtl.applets.repository.data.db.dao.signets

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import ca.etsmtl.applets.repository.data.db.entity.signets.SeanceEntity

/**
 * Created by Sonphil on 02-06-18.
 */
@Dao
abstract class SeanceDao : SignetsDao<SeanceEntity> {
    @Query("SELECT * FROM seanceentity")
    abstract fun getAll(): LiveData<List<SeanceEntity>>

    @Query("SELECT * FROM seanceentity WHERE sigleCours LIKE :sigleCours AND session LIKE :session ORDER BY dateDebut")
    abstract fun getByCoursAndSession(sigleCours: String, session: String): LiveData<List<SeanceEntity>>

    @Query("SELECT * FROM seanceentity WHERE session LIKE :session ORDER BY dateDebut")
    abstract fun getBySession(session: String): LiveData<List<SeanceEntity>>

    @Query("DELETE FROM seanceentity WHERE sigleCours LIKE :sigleCours  AND session LIKE :session")
    abstract fun deleteByCoursAndSession(sigleCours: String, session: String)

    @Query("DELETE FROM seanceentity WHERE session LIKE :session")
    abstract fun deleteBySession(session: String)

    @Transaction
    open fun clearAndInsertByCoursAndSession(sigleCours: String, session: String, seances: List<SeanceEntity>) {
        deleteByCoursAndSession(sigleCours, session)
        insertAll(seances)
    }

    @Transaction
    open fun clearAndInsertBySession(session: String, seances: List<SeanceEntity>) {
        deleteBySession(session)
        insertAll(seances)
    }
}