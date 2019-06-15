package data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Sonphil on 04-06-19.
 */

@Entity
data class DashboardCardEntity(
    @PrimaryKey
    val type: String,
    var visible: Boolean,
    val position: Int,
    val dismissible: Boolean = true
)