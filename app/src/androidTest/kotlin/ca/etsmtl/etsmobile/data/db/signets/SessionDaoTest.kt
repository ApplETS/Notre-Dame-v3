package ca.etsmtl.etsmobile.data.db.signets

import android.support.test.runner.AndroidJUnit4
import ca.etsmtl.etsmobile.LiveDataTestUtil
import ca.etsmtl.etsmobile.data.db.DbTest
import ca.etsmtl.etsmobile.data.db.dao.SessionDao
import ca.etsmtl.etsmobile.data.model.signets.Session
import junit.framework.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by Sonphil on 24-05-18.
 */
@RunWith(AndroidJUnit4::class)
class SessionDaoTest : DbTest() {
    private val entity = Session(
            "É2018",
            "Été 2018",
            "2018-04-30",
            "2018-04-30",
            "2018-04-30",
            "2018-04-30",
            "2018-04-30",
            "2018-04-30",
            "2018-04-30",
            "2018-04-30",
            "2018-04-30",
            "2018-04-30",
            "2018-04-30"
            )
    private lateinit var dao: SessionDao

    @Before
    fun setUp() {
        dao = db.sessionDao()
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
        val same = Session(
                "É2018",
                "Été 2018",
                "2018-03-23",
                "2019-04-30",
                "2019-04-30",
                "2018-04-30",
                "2018-04-30",
                "2018-04-30",
                "2018-04-30",
                "2018-04-30",
                "2018-04-30",
                "2018-04-30",
                "2018-04-30"
        )
        dao.insert(same)
        val fromDb = LiveDataTestUtil.getValue(dao.getAll())
        Assert.assertEquals(same, fromDb[0])
    }
}