package data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import data.db.dao.GitHubContributorDao
import data.db.entity.GitHubContributorEntity

/**
 * Created by Sonphil on 26-09-19.
 */

@Database(
    entities = [
        (GitHubContributorEntity::class)
    ],
    version = 1
)
abstract class GitHubRoomDatabase : RoomDatabase() {
    abstract fun contributorsDao(): GitHubContributorDao
}