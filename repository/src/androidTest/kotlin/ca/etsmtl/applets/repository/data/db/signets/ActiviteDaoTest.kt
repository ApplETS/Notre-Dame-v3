package ca.etsmtl.applets.repository.data.db.signets

import android.support.test.runner.AndroidJUnit4
import ca.etsmtl.applets.repository.LiveDataTestUtil
import ca.etsmtl.applets.repository.data.db.DbTest
import ca.etsmtl.applets.repository.data.db.entity.signets.ActiviteEntity
import junit.framework.Assert
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by Sonphil on 24-05-18.
 */
@RunWith(AndroidJUnit4::class)
class ActiviteDaoTest : DbTest() {
    @Test
    fun testInsert() {
        val entity = ActiviteEntity(
                "LOG210",
                "02",
                1,
                "Lundi",
                "C",
                "Activité de cours",
                "Oui",
                "08:45",
                "12:15",
                "A-1302",
                "Analyse et conception de logiciels"
        )
        db.activiteDao().insert(entity)
        val fromDb = LiveDataTestUtil.getValue(db.activiteDao().getAll())
        Assert.assertNotNull(fromDb)
        Assert.assertEquals(entity, fromDb[0])
    }

    @Test
    fun testInsertSame() {
        val entity = ActiviteEntity(
                "LOG210",
                "02",
                1,
                "Lundi",
                "C",
                "Activité de cours",
                "Oui",
                "08:45",
                "12:15",
                "A-1302",
                "Analyse et conception de logiciels"
        )
        db.activiteDao().insert(entity)
        val same = ActiviteEntity(
                "LOG210",
                "02",
                1,
                "Mardi",
                "C",
                "Activité de cours",
                "Oui",
                "08:45",
                "12:15",
                "A-1234",
                "Analyse et conception de logiciels"
        )
        db.activiteDao().insert(same)
        val fromDb = LiveDataTestUtil.getValue(db.activiteDao().getAll())
        Assert.assertNotNull(fromDb)
        Assert.assertEquals(same, fromDb[0])
    }
}