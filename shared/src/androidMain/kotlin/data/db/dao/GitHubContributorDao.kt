package data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import data.db.entity.GitHubContributorEntity
import kotlinx.coroutines.flow.Flow

/**
 * Created by Sonphil on 26-09-19.
 */

@Dao
abstract class GitHubContributorDao {
    @Query("SELECT * FROM githubcontributorentity")
    abstract fun getAll(): Flow<List<GitHubContributorEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun addAll(contributors: List<GitHubContributorEntity>)

    @Query("DELETE FROM githubcontributorentity")
    abstract fun deleteAll()

    @Transaction
    fun clearAndInsertContributors(contributors: List<GitHubContributorEntity>) {
        deleteAll()
        addAll(contributors)
    }
}