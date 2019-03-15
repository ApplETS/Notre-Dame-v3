package ca.etsmtl.applets.repository.data.db.dao.signets

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import ca.etsmtl.applets.repository.data.db.entity.signets.SessionEntity

/**
 * Created by Sonphil on 24-05-18.
 */
@Dao
abstract class SessionDao : SignetsDao<SessionEntity> {
    @Query("SELECT * FROM sessionentity")
    abstract fun getAll(): LiveData<List<SessionEntity>>

    @Query("DELETE FROM sessionentity")
    abstract fun deleteAll()

    @Transaction
    open fun clearAndInsert(sessions: List<SessionEntity>) {
        deleteAll()
        insertAll(sessions)
    }
}