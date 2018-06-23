package ca.etsmtl.repository.data.db.signets

import android.support.test.runner.AndroidJUnit4
import ca.etsmtl.repository.LiveDataTestUtil
import ca.etsmtl.repository.data.db.DbTest
import ca.etsmtl.repository.data.db.dao.signets.JourRemplaceDao
import ca.etsmtl.repository.data.model.signets.JourRemplace
import junit.framework.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by Sonphil on 24-05-18.
 */
@RunWith(AndroidJUnit4::class)
class JourRemplaceDaoTest : DbTest() {
    private val entity = JourRemplace(
            "2016-05-23",
            "2016-05-25",
            "Journée nationale des Patriotes    "
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
        val same = JourRemplace(
                "2016-05-24",
                "2016-05-25",
                "Journée nationale des Tests    "
        )
        dao.insert(same)
        var fromDb = LiveDataTestUtil.getValue(dao.getAll())
        Assert.assertEquals(same, fromDb[0])

        val foo = JourRemplace(
                "2016-05-23",
                "2016-05-26",
                "Foo    "
        )
        dao.insert(foo)
        fromDb = LiveDataTestUtil.getValue(dao.getAll())
        Assert.assertEquals(same, fromDb[0])
        Assert.assertEquals(foo, fromDb[1])
    }
}