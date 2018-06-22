package ca.etsmtl.repository.data.db.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import ca.etsmtl.repository.data.model.signets.Session

/**
 * Created by Sonphil on 24-05-18.
 */
@Dao
abstract class SessionDao : SignetsDao<Session> {
    @Query("SELECT * FROM session")
    abstract fun getAll(): LiveData<List<Session>>
}