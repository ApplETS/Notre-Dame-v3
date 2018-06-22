package ca.etsmtl.repository.data.db.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import ca.etsmtl.repository.data.model.signets.Programme

/**
 * Created by Sonphil on 17-05-18.
 */
@Dao
abstract class ProgrammeDao : SignetsDao<Programme> {
    @Query("SELECT * FROM programme")
    abstract fun getAll(): LiveData<List<Programme>>

    @Query("DELETE FROM programme")
    abstract fun deleteAll()
}