package ca.etsmtl.applets.repository.data.db.signets

import android.support.test.runner.AndroidJUnit4
import ca.etsmtl.applets.repository.LiveDataTestUtil
import ca.etsmtl.applets.repository.data.db.DbTest
import ca.etsmtl.applets.repository.data.db.dao.signets.SommaireElementsEvaluationDao
import ca.etsmtl.applets.repository.data.db.entity.signets.SommaireElementsEvaluationEntity
import junit.framework.Assert
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by Sonphil on 26-05-18.
 */
@RunWith(AndroidJUnit4::class)
class SommaireElementsEvaluationDaoTest : DbTest() {
    private val sommaireEvaluations = SommaireElementsEvaluationEntity(
            "INF111",
            "E2018",
            "65,9",
            "100",
            "65,9",
            "70,6",
            "70,6",
            "18,2",
            "99,0",
            "96,4",
            "99,0",
            "96,4"
    )
    private lateinit var dao: SommaireElementsEvaluationDao

    @Before
    fun setUp() {
        dao = db.sommaireElementsEvaluationDao()
        dao.insert(sommaireEvaluations)
    }

    @Test
    fun testInsert() {
        val fromDb = LiveDataTestUtil.getValue(dao.getAll())
        Assert.assertNotNull(fromDb)
        Assert.assertEquals(sommaireEvaluations, fromDb[0])
    }

    @Test
    fun testInsertSame() {
        val same = SommaireElementsEvaluationEntity(
                "INF111",
                "E2018",
                "65,9",
                "100",
                "65,9",
                "70,6",
                "70,6",
                "65,9",
                "96,4",
                "65,9",
                "65,9",
                "65,5"
        )
        dao.insert(same)
        val fromDb = LiveDataTestUtil.getValue(dao.getAll())
        Assert.assertEquals(fromDb.size, 1)
        Assert.assertEquals(same, fromDb[0])
    }

    @Test
    fun testGetBySigleCoursAndSession() {
        val expectedSommaire = SommaireElementsEvaluationEntity(
                "INF111",
                "E2018",
                "65,9",
                "100",
                "65,9",
                "70,6",
                "70,6",
                "18,2",
                "99,0",
                "96,4",
                "99,0",
                "96,4"
        )

        val unexpectedSommaire1 = SommaireElementsEvaluationEntity(
                "LOG123",
                expectedSommaire.session,
                "65,9",
                "100",
                "65,9",
                "70,6",
                "70,6",
                "18,2",
                "99,0",
                "96,4",
                "99,0",
                "96,4"
        )
        dao.insert(unexpectedSommaire1)

        val unexpectedSommaire2 = SommaireElementsEvaluationEntity(
                expectedSommaire.sigleCours,
                "A2018",
                "65,9",
                "100",
                "65,9",
                "70,6",
                "70,6",
                "18,2",
                "99,0",
                "96,4",
                "99,0",
                "96,4"
        )
        dao.insert(unexpectedSommaire2)

        dao.insert(expectedSommaire)

        val sommaire = LiveDataTestUtil.getValue(dao.getBySigleCoursAndSession(
                expectedSommaire.sigleCours,
                expectedSommaire.session
        ))
        assertEquals(expectedSommaire, sommaire)
    }

    @Test
    fun testDeleteBySigleCoursAndSession() {
        var sommaires = LiveDataTestUtil.getValue(dao.getAll())
        assertEquals(1, sommaires.size)

        dao.deleteBySigleCoursAndSession("LOG123", sommaireEvaluations.session)
        sommaires = LiveDataTestUtil.getValue(dao.getAll())
        assertEquals(1, sommaires.size)

        dao.deleteBySigleCoursAndSession(sommaireEvaluations.sigleCours, "A2018")
        sommaires = LiveDataTestUtil.getValue(dao.getAll())
        assertEquals(1, sommaires.size)

        dao.deleteBySigleCoursAndSession(sommaireEvaluations.sigleCours, sommaireEvaluations.session)
        sommaires = LiveDataTestUtil.getValue(dao.getAll())
        assertEquals(0, sommaires.size)
    }
}