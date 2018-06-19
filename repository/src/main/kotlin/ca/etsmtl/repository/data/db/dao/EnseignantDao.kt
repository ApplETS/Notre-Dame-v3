package ca.etsmtl.repos.data.db.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import ca.etsmtl.repos.data.model.signets.Enseignant

/**
 * Created by Sonphil on 24-05-18.
 */
@Dao
abstract class EnseignantDao : SignetsDao<Enseignant> {
    @Query("SELECT * FROM enseignant")
    abstract fun getAll(): LiveData<List<Enseignant>>
}