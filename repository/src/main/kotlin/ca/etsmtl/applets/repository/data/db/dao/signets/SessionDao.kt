package ca.etsmtl.applets.repository.data.db.dao.signets

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
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
}