package ca.etsmtl.applets.repository.data.db.signets

import androidx.test.runner.AndroidJUnit4
import ca.etsmtl.applets.repository.LiveDataTestUtil
import ca.etsmtl.applets.repository.data.db.DbTest
import ca.etsmtl.applets.repository.data.db.dao.signets.EvaluationDao
import ca.etsmtl.applets.repository.data.db.entity.signets.EvaluationEntity
import junit.framework.Assert
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
    private val entity = EvaluationEntity(
            "INF111",
            "01",
            "E2018",
            "TP03",
            "",
            "",
            "89,0",
            "100",
            "89",
            "20",
            "65,1",
            "65,1",
            "18,4",
            "68,0",
            "91",
            true,
            "",
            false
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
        val same = EvaluationEntity(
                "INF111",
                "01",
                "E2018",
                "TP03",
                "",
                "",
                "99,0",
                "100",
                "99",
                "20",
                "65,1",
                "65,1",
                "18,4",
                "68,0",
                "99",
                true,
                "",
                false
        )
        dao.insert(same)
        val fromDb = LiveDataTestUtil.getValue(dao.getAll())
        assertEquals(same, fromDb[0])
    }

    @Test
    fun testGetByCoursGroupeAndSession() {
        val expectedEvaluation = EvaluationEntity(
                "LOG123",
                "01",
                "E2018",
                "TP03",
                "",
                "",
                "89,0",
                "100",
                "89,0",
                "20",
                "65,1",
                "18,4",
                "65,1",
                "68,0",
                "91",
                true,
                "",
                false
        )
        dao.insert(expectedEvaluation)

        val unexpectedEvaluation1 = EvaluationEntity(
                "LOG123",
                "01",
                "H2018",
                "TP03",
                "",
                "",
                "89,0",
                "100",
                "89,0",
                "20",
                "65,1",
                "65,1",
                "18,4",
                "68,0",
                "91",
                true,
                "",
                false
        )
        dao.insert(unexpectedEvaluation1)

        val unexpectedEvaluation2 = EvaluationEntity(
                "LOG123",
                "02",
                "E2018",
                "TP03",
                "",
                "",
                "89,0",
                "100",
                "89,0",
                "20",
                "65,1",
                "18,4",
                "65,1",
                "68,0",
                "91",
                true,
                "",
                false
        )
        dao.insert(unexpectedEvaluation2)

        assertEquals(4, LiveDataTestUtil.getValue(dao.getAll()).size)

        val evaluations = LiveDataTestUtil.getValue(dao.getByCoursGroupeAndSession(
                expectedEvaluation.cours,
                expectedEvaluation.groupe,
                expectedEvaluation.session
        ))
        assertEquals(1, evaluations.size)
        assertEquals(expectedEvaluation, evaluations[0])
    }

    @Test
    fun deleteByCoursGroupeAndSession() {
        var evaluations = LiveDataTestUtil.getValue(dao.getAll())
        Assert.assertEquals(1, evaluations.size)

        dao.deleteByCoursGroupeAndSession("LOG123", entity.groupe, entity.session)
        evaluations = LiveDataTestUtil.getValue(dao.getAll())
        Assert.assertEquals(1, evaluations.size)

        dao.deleteByCoursGroupeAndSession(entity.cours, "02", entity.session)
        evaluations = LiveDataTestUtil.getValue(dao.getAll())
        Assert.assertEquals(1, evaluations.size)

        dao.deleteByCoursGroupeAndSession(entity.cours, entity.groupe, "A2018")
        evaluations = LiveDataTestUtil.getValue(dao.getAll())
        Assert.assertEquals(1, evaluations.size)

        dao.deleteByCoursGroupeAndSession(entity.cours, entity.groupe, entity.session)
        evaluations = LiveDataTestUtil.getValue(dao.getAll())
        Assert.assertEquals(0, evaluations.size)
    }
}