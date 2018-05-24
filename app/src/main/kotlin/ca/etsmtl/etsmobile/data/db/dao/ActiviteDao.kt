package ca.etsmtl.etsmobile.data.db.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import ca.etsmtl.etsmobile.data.model.signets.Activite

/**
 * Created by Sonphil on 24-05-18.
 */
@Dao
abstract class ActiviteDao : SignetsDao<Activite> {
    @Query("SELECT * FROM activite")
    abstract fun getAll(): LiveData<List<Activite>>
}