package ca.etsmtl.etsmobile.data.db.signets

import android.support.test.runner.AndroidJUnit4
import ca.etsmtl.etsmobile.LiveDataTestUtil
import ca.etsmtl.etsmobile.data.db.DbTest
import ca.etsmtl.etsmobile.data.db.dao.EvaluationDao
import ca.etsmtl.etsmobile.data.model.signets.Evaluation
import junit.framework.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by Sonphil on 24-05-18.
 */
@RunWith(AndroidJUnit4::class)
class EvaluationDaoTest : DbTest() {
    private val entity = Evaluation(
            "INF111-01",
            "TP03",
            "01",
            "",
            "89,0",
            "100",
            "20",
            "65,1",
            "18,4",
            "68,0",
            "91",
            "Oui",
            "",
            "Non"
    )
    private lateinit var dao: EvaluationDao

    @Before
    fun setUp() {
        dao = db.evaluationDao()
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
        val same = Evaluation(
                "INF111-01",
                "TP03",
                "01",
                "",
                "99,0",
                "100",
                "20",
                "65,1",
                "18,4",
                "68,0",
                "99",
                "Oui",
                "",
                "Non"
        )
        dao.insert(same)
        val fromDb = LiveDataTestUtil.getValue(dao.getAll())
        Assert.assertEquals(same, fromDb[0])
    }
}