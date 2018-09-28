package ca.etsmtl.repository.data.db.dao.signets

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import ca.etsmtl.repository.data.db.entity.signets.EnseignantEntity

/**
 * Created by Sonphil on 24-05-18.
 */
@Dao
abstract class EnseignantDao : SignetsDao<EnseignantEntity> {
    @Query("SELECT * FROM enseignantentity")
    abstract fun getAll(): LiveData<List<EnseignantEntity>>
}