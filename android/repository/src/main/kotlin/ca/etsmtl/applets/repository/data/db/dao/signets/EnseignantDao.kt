package ca.etsmtl.applets.repository.data.db.dao.signets

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import ca.etsmtl.applets.repository.data.db.entity.signets.EnseignantEntity

/**
 * Created by Sonphil on 24-05-18.
 */
@Dao
abstract class EnseignantDao : SignetsDao<EnseignantEntity> {
    @Query("SELECT * FROM enseignantentity")
    abstract fun getAll(): LiveData<List<EnseignantEntity>>
}