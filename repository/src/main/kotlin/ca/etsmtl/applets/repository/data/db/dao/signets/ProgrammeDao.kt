package ca.etsmtl.applets.repository.data.db.dao.signets

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import ca.etsmtl.applets.repository.data.db.entity.signets.ProgrammeEntity

/**
 * Created by Sonphil on 17-05-18.
 */
@Dao
abstract class ProgrammeDao : SignetsDao<ProgrammeEntity> {
    @Query("SELECT * FROM programmeentity")
    abstract fun getAll(): LiveData<List<ProgrammeEntity>>

    @Query("DELETE FROM programmeentity")
    abstract fun deleteAll()
}