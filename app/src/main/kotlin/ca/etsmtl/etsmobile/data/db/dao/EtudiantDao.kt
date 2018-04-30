package ca.etsmtl.etsmobile.data.db.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import ca.etsmtl.etsmobile.data.model.signets.Etudiant

/**
 * Created by Sonphil on 13-03-18.
 */
@Dao
abstract class EtudiantDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertEtudiant(etudiant: Etudiant)

    @Query("SELECT * FROM etudiant LIMIT 1")
    abstract fun getEtudiant(): LiveData<Etudiant>

    @Query("DELETE FROM etudiant")
    abstract fun deleteAll()
}