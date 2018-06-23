package ca.etsmtl.repository.data.db.dao.signets

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import ca.etsmtl.repository.data.model.signets.HoraireExamenFinal

/**
 * Created by Sonphil on 23-05-18.
 */
@Dao
abstract class HoraireExamenFinalDao : SignetsDao<HoraireExamenFinal> {
    @Query("SELECT * FROM horaireexamenfinal")
    abstract fun getAll(): LiveData<List<HoraireExamenFinal>>
}