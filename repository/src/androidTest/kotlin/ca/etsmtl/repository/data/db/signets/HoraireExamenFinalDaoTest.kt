package ca.etsmtl.repository.data.db.signets

import android.support.test.runner.AndroidJUnit4
import ca.etsmtl.repository.LiveDataTestUtil
import ca.etsmtl.repository.data.db.DbTest
import ca.etsmtl.repository.data.db.entity.signets.HoraireExamenFinalEntity
import junit.framework.Assert
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by Sonphil on 23-05-18.
 */
@RunWith(AndroidJUnit4::class)
class HoraireExamenFinalDaoTest : DbTest() {
    @Test
    fun testInsert() {
        val horaire = HoraireExamenFinalEntity(
                "LOG330",
                "01",
                "2018-04-13",
                "13:30",
                "16:30",
                "A-1302",
                "H2018"
        )
        db.horaireExamenFinalDao().insert(horaire)
        val fromDb = LiveDataTestUtil.getValue(db.horaireExamenFinalDao().getAll())
        Assert.assertNotNull(fromDb)
        Assert.assertEquals(horaire, fromDb[0])
    }

    @Test
    fun testInsertSame() {
        val horaire = HoraireExamenFinalEntity(
                "LOG330",
                "01",
                "2018-04-13",
                "13:30",
                "16:30",
                "A-1302",
                "H2018"
        )
        db.horaireExamenFinalDao().insert(horaire)
        val same = HoraireExamenFinalEntity(
                "LOG330",
                "01",
                "2018-04-13",
                "13:30",
                "16:30",
                "A-1234",
                "H2018"
        )
        db.horaireExamenFinalDao().insert(same)
        val fromDb = LiveDataTestUtil.getValue(db.horaireExamenFinalDao().getAll())
        Assert.assertNotNull(fromDb)
        Assert.assertEquals(same, fromDb[0])
    }
}