package ca.etsmtl.applets.repository.data.db.dao.signets

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
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