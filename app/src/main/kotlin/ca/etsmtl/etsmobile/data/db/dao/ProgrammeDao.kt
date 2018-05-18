package ca.etsmtl.etsmobile.data.db.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import ca.etsmtl.etsmobile.data.model.signets.Programme

/**
 * Created by Sonphil on 17-05-18.
 */
@Dao
abstract class ProgrammeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(programme: Programme)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertAll(vararg programmes: Programme)

    @Query("SELECT * FROM programme")
    abstract fun getAll(): LiveData<List<Programme>>

    @Query("DELETE FROM programme")
    abstract fun deleteAll()
}