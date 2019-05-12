package extension

import ca.etsmtl.applets.shared.db.DashboardCardEntity
import ca.etsmtl.applets.shared.db.EtsMobileDb
import com.squareup.sqldelight.ColumnAdapter
import com.squareup.sqldelight.db.SqlDriver
import model.DashboardCard

/**
 * Created by Sonphil on 10-03-19.
 */

fun SqlDriver.createDb(): EtsMobileDb {
    val adapter = DashboardCardEntity.Adapter(object : ColumnAdapter<DashboardCard.Type, String> {
        override fun decode(databaseValue: String): DashboardCard.Type {
            return DashboardCard.Type.valueOf(databaseValue)
        }

        override fun encode(value: DashboardCard.Type): String = value.name
    })

    return EtsMobileDb(this, adapter)
}