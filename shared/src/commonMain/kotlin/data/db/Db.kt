package data.db

import ca.etsmtl.applets.shared.db.DashboardCardEntity
import ca.etsmtl.applets.shared.db.EtsMobileDb
import com.squareup.sqldelight.ColumnAdapter
import com.squareup.sqldelight.db.SqlDriver
import model.DashboardCard

/**
 * Created by Sonphil on 09-02-19.
 */

object Db {
    private var driverRef: SqlDriver? = null
    private var dbRef: EtsMobileDb? = null

    val ready: Boolean
        get() = driverRef != null

    fun setupDb(driver: SqlDriver) {
        val adapter = DashboardCardEntity.Adapter(object : ColumnAdapter<DashboardCard.Type, String> {
            override fun decode(databaseValue: String): DashboardCard.Type {
                return DashboardCard.Type.valueOf(databaseValue)
            }

            override fun encode(value: DashboardCard.Type): String = value.name
        })
        val db = EtsMobileDb(driver, adapter)

        driverRef = driver
        dbRef = db
    }

    internal fun clearDb() {
        driverRef!!.close()
        dbRef = null
        driverRef = null
    }

    val instance: EtsMobileDb
        get() = dbRef!!
}