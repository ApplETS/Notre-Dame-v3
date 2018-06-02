package ca.etsmtl.etsmobile.data.db.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import ca.etsmtl.etsmobile.data.model.signets.Seance

/**
 * Created by Sonphil on 02-06-18.
 */
@Dao
abstract class SeanceDao : SignetsDao<Seance> {
    @Query("SELECT * FROM seance")
    abstract fun getAll(): LiveData<List<Seance>>
}