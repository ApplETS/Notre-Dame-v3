package ca.etsmtl.etsmobile.data.db.signets

import android.support.test.runner.AndroidJUnit4
import ca.etsmtl.etsmobile.LiveDataTestUtil
import ca.etsmtl.etsmobile.data.db.DbTest
import ca.etsmtl.etsmobile.data.db.dao.SommaireElementsEvaluationDao
import ca.etsmtl.etsmobile.data.model.signets.SommaireElementsEvaluation
import junit.framework.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by Sonphil on 26-05-18.
 */
@RunWith(AndroidJUnit4::class)
class SommaireElementsEvaluationDaoTest : DbTest() {
    private val entity = SommaireElementsEvaluation(
            "INF111",
            "01",
            "65,9",
            "18,2",
            "70,6",
            "99,0",
            "96,4",
            "99,0",
            "96,4",
            "65,5"
    )
    private lateinit var dao: SommaireElementsEvaluationDao

    @Before
    fun setUp() {
        dao = db.sommaireElementsEvaluationDao()
        dao.insert(entity)
    }

    @Test
    fun testInsert() {
        val fromDb = LiveDataTestUtil.getValue(dao.getAll())
        Assert.assertNotNull(fromDb)
        Assert.assertEquals(entity, fromDb[0])
    }

    @Test
    fun testInsertWithDifferentSigleOrGroupe() {
        val diffSigle = SommaireElementsEvaluation(
                "ING111",
                "01",
                "65,9",
                "18,2",
                "70,6",
                "99,0",
                "96,4",
                "99,0",
                "96,4",
                "65,5"
        )
        val diffGroupe = SommaireElementsEvaluation(
                "INF111",
                "02",
                "65,9",
                "18,2",
                "70,6",
                "99,0",
                "96,4",
                "99,0",
                "96,4",
                "65,5"
        )
        dao.insert(diffSigle)
        dao.insert(diffGroupe)
        val fromDb = LiveDataTestUtil.getValue(dao.getAll())
        Assert.assertEquals(entity, fromDb[0])
        Assert.assertEquals(diffSigle, fromDb[1])
        Assert.assertEquals(diffGroupe, fromDb[2])
    }

    @Test
    fun testInsertSame() {
        val same = SommaireElementsEvaluation(
                "INF111",
                "01",
                "65,9",
                "65,9",
                "70,6",
                "65,9",
                "96,4",
                "65,9",
                "65,9",
                "65,5"
        )
        dao.insert(same)
        val fromDb = LiveDataTestUtil.getValue(dao.getAll())
        Assert.assertEquals(same, fromDb[0])
    }
}