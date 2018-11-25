package ca.etsmtl.applets.repository.data.db.dao.signets

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import ca.etsmtl.applets.repository.data.db.entity.signets.EtudiantEntity

/**
 * Created by Sonphil on 13-03-18.
 */
@Dao
abstract class EtudiantDao : SignetsDao<EtudiantEntity> {
    @Query("SELECT * FROM etudiantentity")
    abstract fun getAll(): LiveData<List<EtudiantEntity>>

    @Query("DELETE FROM etudiantentity")
    abstract fun deleteAll()
}