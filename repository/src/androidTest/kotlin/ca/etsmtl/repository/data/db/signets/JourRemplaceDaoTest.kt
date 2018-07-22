package ca.etsmtl.repository.data.db.signets

import android.support.test.runner.AndroidJUnit4
import ca.etsmtl.repository.LiveDataTestUtil
import ca.etsmtl.repository.data.db.DbTest
import ca.etsmtl.repository.data.db.dao.signets.JourRemplaceDao
import ca.etsmtl.repository.data.db.entity.signets.JourRemplaceEntity
import junit.framework.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by Sonphil on 24-05-18.
 */
@RunWith(AndroidJUnit4::class)
class JourRemplaceDaoTest : DbTest() {
    private val entity = JourRemplaceEntity(
            "2016-05-23",
            "2016-05-25",
            "Journée nationale des Patriotes    ",
            "E2018"
    )
    private lateinit var dao: JourRemplaceDao

    @Before
    fun setUp() {
        dao = db.jourRemplaceDao()
        dao.insert(entity)
    }

    @Test
    fun testInsert() {
        val fromDb = LiveDataTestUtil.getValue(dao.getAll())
        Assert.assertNotNull(fromDb)
        Assert.assertEquals(entity, fromDb[0])
    }

    @Test
    fun testInsertSame() {
        val same = JourRemplaceEntity(
                "2016-05-24",
                "2016-05-25",
                "Journée nationale des Tests    ",
                "E2018"
        )
        dao.insert(same)
        var fromDb = LiveDataTestUtil.getValue(dao.getAll())
        Assert.assertEquals(same, fromDb[0])

        val foo = JourRemplaceEntity(
                "2016-05-23",
                "2016-05-26",
                "Foo    ",
                "E2018"
        )
        dao.insert(foo)
        fromDb = LiveDataTestUtil.getValue(dao.getAll())
        Assert.assertEquals(same, fromDb[0])
        Assert.assertEquals(foo, fromDb[1])
    }

    @Test
    fun testGetBySession() {
        val jour1E2018 = JourRemplaceEntity(
                "2016-06-24",
                "2016-06-25",
                "Journée nationale des Tests    ",
                "E2018"
        )
        dao.insert(jour1E2018)
        val jour2E2018 = JourRemplaceEntity(
                "2016-07-24",
                "2016-07-25",
                "Journée nationale des Tests    ",
                "E2018"
        )
        dao.insert(jour2E2018)

        val jourH2018 = JourRemplaceEntity(
                "2016-02-24",
                "2016-02-25",
                "Foo    ",
                "H2018"
        )
        dao.insert(jourH2018)

        val joursRemplacesE2018 = LiveDataTestUtil.getValue(db.jourRemplaceDao().getAllBySession("E2018"))
        Assert.assertEquals(3, joursRemplacesE2018.size)
        Assert.assertEquals(entity, joursRemplacesE2018[0])
        Assert.assertEquals(jour1E2018, joursRemplacesE2018[1])
        Assert.assertEquals(jour2E2018, joursRemplacesE2018[2])
        val joursRemplacesH2018 = LiveDataTestUtil.getValue(db.jourRemplaceDao().getAllBySession("H2018"))
        Assert.assertEquals(1, joursRemplacesH2018.size)
        Assert.assertEquals(jourH2018, joursRemplacesH2018[0])
    }

    @Test
    fun testDeleteBySession() {
        db.horaireExamenFinalDao().deleteBySession("A2018")
        var joursRemplaces = LiveDataTestUtil.getValue(db.jourRemplaceDao().getAll())
        Assert.assertEquals(1, joursRemplaces.size)
        db.horaireExamenFinalDao().deleteBySession("E2018")
        joursRemplaces = LiveDataTestUtil.getValue(db.jourRemplaceDao().getAll())
        Assert.assertEquals(1, joursRemplaces.size)
    }
}