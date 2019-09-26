package data.db.dao

import androidx.room.Dao
import androidx.room.Query
import data.db.entity.GitHubContributorEntity
import kotlinx.coroutines.flow.Flow

/**
 * Created by Sonphil on 26-09-19.
 */

@Dao
interface GitHubContributorDao {
    @Query("SELECT * FROM githubcontributorentity")
    fun getAll(): Flow<List<GitHubContributorEntity>>
}