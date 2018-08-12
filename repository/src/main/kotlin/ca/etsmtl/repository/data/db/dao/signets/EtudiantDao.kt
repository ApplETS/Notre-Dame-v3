package ca.etsmtl.repository.data.db.dao.signets

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import ca.etsmtl.repository.data.db.entity.signets.EtudiantEntity

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