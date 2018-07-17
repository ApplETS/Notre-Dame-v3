package ca.etsmtl.repository.data.db.dao.signets

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import android.arch.persistence.room.Transaction
import ca.etsmtl.repository.data.db.entity.signets.HoraireExamenFinalEntity

/**
 * Created by Sonphil on 23-05-18.
 */
@Dao
abstract class HoraireExamenFinalDao : SignetsDao<HoraireExamenFinalEntity> {
    @Query("SELECT * FROM horaireexamenfinalentity")
    abstract fun getAll(): LiveData<List<HoraireExamenFinalEntity>>

    @Query("SELECT * FROM horaireexamenfinalentity WHERE session LIKE :session")
    abstract fun getAllBySession(session: String): LiveData<List<HoraireExamenFinalEntity>>

    @Query("DELETE FROM horaireexamenfinalentity WHERE session LIKE :session")
    abstract fun deleteBySession(session: String)

    @Transaction
    open fun clearAndInsertBySession(session: String, horaireExamenFinalEntities: List<HoraireExamenFinalEntity>) {
        deleteBySession(session)
        insertAll(horaireExamenFinalEntities)
    }
}