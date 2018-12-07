package ca.etsmtl.applets.repository.data.db.signets

import androidx.test.runner.AndroidJUnit4
import ca.etsmtl.applets.repository.LiveDataTestUtil
import ca.etsmtl.applets.repository.data.db.DbTest
import ca.etsmtl.applets.repository.data.db.dao.signets.EvaluationCoursDao
import ca.etsmtl.applets.repository.data.db.entity.signets.EvaluationCoursEntity
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class EvaluationCoursDaoTest : DbTest() {
    private val entity = EvaluationCoursEntity(
        "H2018",
        1531454400000,
        1532318340000,
        "Leonhard, Euler",
        true,
        "02",
        "MAT472",
        "Cours"
    )
    private lateinit var dao: EvaluationCoursDao

    @Before
    fun setUp() {
        dao = db.evaluationCoursDao()
        dao.insert(entity)
    }

    @Test
    fun testInsert() {
        val fromDb = LiveDataTestUtil.getValue(dao.getEvaluationCoursBySession(entity.session))
        assertNotNull(fromDb)
        assertEquals(entity, fromDb[0])
    }

    @Test
    fun testGetBySession() {
        val expected = EvaluationCoursEntity(
            "E2018",
            1531454400000,
            1532318340000,
            "Leonhard, Euler",
            true,
            "02",
            "MAT472",
            "Cours"
        )
        dao.insert(expected)
        dao.insert(EvaluationCoursEntity(
            "H2018",
            1531454400000,
            1532318340000,
            "Leonhard, Euler",
            true,
            "02",
            "MAT472",
            "Cours"
        ))

        val evaluations = LiveDataTestUtil.getValue(dao.getEvaluationCoursBySession(expected.session))
        assertEquals(1, evaluations.size)
        assertEquals(expected, evaluations[0])
    }

    @Test
    fun testDeleteBySession() {
        dao.deleteBySession("A2023")
        var evaluations = LiveDataTestUtil.getValue(dao.getAll())
        assertEquals(1, evaluations.count())
        dao.deleteBySession(entity.session)
        assertEquals(0, evaluations.size)
    }
}