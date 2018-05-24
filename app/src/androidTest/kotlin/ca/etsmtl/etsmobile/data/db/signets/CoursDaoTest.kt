package ca.etsmtl.etsmobile.data.db.signets

import android.support.test.runner.AndroidJUnit4
import ca.etsmtl.etsmobile.LiveDataTestUtil.getValue
import ca.etsmtl.etsmobile.data.db.DbTest
import ca.etsmtl.etsmobile.data.db.dao.CoursDao
import ca.etsmtl.etsmobile.data.model.signets.Cours
import junit.framework.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by Sonphil on 24-05-18.
 */
@RunWith(AndroidJUnit4::class)
class CoursDaoTest : DbTest() {

    private val entity = Cours(
            "LOG530",
            "01",
            "H2018",
            "7365",
            "A",
            3,
            "Réingénierie du logiciel"
    )
    private lateinit var dao: CoursDao

    @Before
    fun setUp() {
        dao = db.coursDao()
        dao.insert(entity)
    }

    @Test
    fun testInsert() {
        val fromDb = getValue(dao.getAll())
        Assert.assertNotNull(fromDb)
        Assert.assertEquals(entity, fromDb[0])
    }

    @Test
    fun testInsertSame() {
        val same = Cours(
                "LOG530",
                "01",
                "H2018",
                "7365",
                "B+",
                3,
                "Réingénierie du logiciel"
        )
        dao.insert(same)
        val fromDb = getValue(dao.getAll())
        Assert.assertNotNull(fromDb)
        Assert.assertEquals(same, fromDb[0])
    }
}