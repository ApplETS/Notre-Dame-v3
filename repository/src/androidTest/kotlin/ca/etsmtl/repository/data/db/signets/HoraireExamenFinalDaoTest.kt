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

    @Test
    fun testGetBySession() {
        val horaireH2018 = HoraireExamenFinalEntity(
                "LOG330",
                "01",
                "2018-04-13",
                "13:30",
                "16:30",
                "A-1302",
                "H2018"
        )
        db.horaireExamenFinalDao().insert(horaireH2018)
        val horaire2H2018 = HoraireExamenFinalEntity(
                "LOG123",
                "01",
                "2018-04-12",
                "12:30",
                "15:30",
                "A-4321",
                "H2018"
        )
        db.horaireExamenFinalDao().insert(horaire2H2018)
        val horaireE2018 = HoraireExamenFinalEntity(
                "MAT472",
                "01",
                "2018-07-17",
                "13:30",
                "16:30",
                "A-1302",
                "E2018"
        )
        db.horaireExamenFinalDao().insert(horaireE2018)
        val horairesH2018 = LiveDataTestUtil.getValue(db.horaireExamenFinalDao().getAllBySession("H2018"))
        Assert.assertEquals(2, horairesH2018.size)
        Assert.assertEquals(horaireH2018, horairesH2018[0])
        Assert.assertEquals(horaire2H2018, horairesH2018[1])
        val horairesE2018 = LiveDataTestUtil.getValue(db.horaireExamenFinalDao().getAllBySession("E2018"))
        Assert.assertEquals(1, horairesE2018.size)
        Assert.assertEquals(horaireE2018, horairesE2018[0])
    }
}