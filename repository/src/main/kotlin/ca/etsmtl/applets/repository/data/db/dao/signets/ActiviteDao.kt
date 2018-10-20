package ca.etsmtl.applets.repository.data.db.dao.signets

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import ca.etsmtl.applets.repository.data.db.entity.signets.ActiviteEntity

/**
 * Created by Sonphil on 24-05-18.
 */
@Dao
abstract class ActiviteDao : SignetsDao<ActiviteEntity> {
    @Query("SELECT * FROM activiteentity")
    abstract fun getAll(): LiveData<List<ActiviteEntity>>
}