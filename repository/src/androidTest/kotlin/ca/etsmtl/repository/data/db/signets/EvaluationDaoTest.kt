package ca.etsmtl.repository.data.db.signets

import android.support.test.runner.AndroidJUnit4
import ca.etsmtl.repository.LiveDataTestUtil
import ca.etsmtl.repository.data.db.DbTest
import ca.etsmtl.repository.data.db.dao.signets.EvaluationDao
import ca.etsmtl.repository.data.model.signets.Evaluation
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
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
        assertNotNull(fromDb)
        assertEquals(entity, fromDb[0])
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
        assertEquals(same, fromDb[0])
    }

    @Test
    fun testGetByCoursGroupe() {
        val expectedEvaluation = Evaluation(
                "LOG123-01",
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
        dao.insert(expectedEvaluation)

        val unexpectedEvaluation = Evaluation(
                "LOG123-02",
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
        dao.insert(unexpectedEvaluation)

        val evaluations = LiveDataTestUtil.getValue(dao.getByCoursGroupe("LOG123-01"))
        assertEquals(1, evaluations.size)
        assertEquals(expectedEvaluation, evaluations[0])
    }
}