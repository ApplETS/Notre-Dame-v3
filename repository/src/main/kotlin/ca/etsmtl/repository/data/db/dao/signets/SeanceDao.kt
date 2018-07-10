package ca.etsmtl.repository.data.db.dao.signets

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import ca.etsmtl.repository.data.db.entity.signets.SeanceEntity

/**
 * Created by Sonphil on 02-06-18.
 */
@Dao
abstract class SeanceDao : SignetsDao<SeanceEntity> {
    @Query("SELECT * FROM seanceentity")
    abstract fun getAll(): LiveData<List<SeanceEntity>>
}