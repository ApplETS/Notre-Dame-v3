package ca.etsmtl.repository.data.db.dao.signets

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import ca.etsmtl.repository.data.db.entity.signets.JourRemplaceEntity

/**
 * Created by Sonphil on 24-05-18.
 */
@Dao
abstract class JourRemplaceDao : SignetsDao<JourRemplaceEntity> {
    @Query("SELECT * FROM jourremplaceentity")
    abstract fun getAll(): LiveData<List<JourRemplaceEntity>>

    @Query("SELECT * FROM jourremplaceentity WHERE session LIKE :session")
    abstract fun getAllBySession(session: String): LiveData<List<JourRemplaceEntity>>
}