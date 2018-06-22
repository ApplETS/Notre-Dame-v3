package ca.etsmtl.repository.data.db.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import ca.etsmtl.repository.data.model.signets.Etudiant

/**
 * Created by Sonphil on 13-03-18.
 */
@Dao
abstract class EtudiantDao : SignetsDao<Etudiant> {
    @Query("SELECT * FROM etudiant")
    abstract fun getAll(): LiveData<List<Etudiant>>

    @Query("DELETE FROM etudiant")
    abstract fun deleteAll()
}