package ca.etsmtl.etsmobile.data.db.dao

import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy

/**
 * Created by Sonphil on 17-05-18.
 */

interface SignetsDao<T> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(obj: T)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg obj: T)
}