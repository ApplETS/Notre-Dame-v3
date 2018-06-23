package ca.etsmtl.repository.data.db.signets

import android.support.test.runner.AndroidJUnit4
import ca.etsmtl.repository.LiveDataTestUtil.getValue
import ca.etsmtl.repository.data.db.DbTest
import ca.etsmtl.repository.data.db.dao.signets.CoursDao
import ca.etsmtl.repository.data.model.signets.Cours
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
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
        assertNotNull(fromDb)
        assertEquals(entity, fromDb[0])
    }

    @Test
    fun testInsertSameSigleDifferentSession() {
        val sameSigle = Cours(
                "LOG530",
                "01",
                "E2018",
                "7365",
                "B+",
                3,
                "Réingénierie du logiciel"
        )

        dao.insert(sameSigle)
        val fromDb = getValue(dao.getAll())
        assertEquals(entity, fromDb[0])
        assertEquals(sameSigle, fromDb[1])
    }

    @Test
    fun testInsertSameSessionDifferentSigle() {
        val sameSession = Cours(
                "LOG123",
                "01",
                "H2018",
                "7365",
                "B+",
                3,
                "Titre"
        )

        dao.insert(sameSession)
        val fromDb = getValue(dao.getAll())
        assertEquals(entity, fromDb[0])
        assertEquals(sameSession, fromDb[1])
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
                "Foo"
        )

        dao.insert(same)
        val fromDb = getValue(dao.getAll())
        assertEquals(fromDb.size, 1)
        assertEquals(same, fromDb[0])
    }

    @Test
    fun testGetCoursesSession() {
        val sessionAbrege = "É2018"

        val coursSession1 = Cours(
                "LOG530",
                "01",
                sessionAbrege,
                "7365",
                "B+",
                3,
                "Réingénierie du logiciel"
        )
        dao.insert(coursSession1)

        val coursSession2 = Cours(
                "FOO123",
                "01",
                sessionAbrege,
                "7365",
                "B+",
                3,
                "Foo"
        )
        dao.insert(coursSession2)

        val coursDifferentSession = Cours(
                "LOG123",
                "01",
                "H2018",
                "7365",
                "B+",
                3,
                "Foo"
        )
        dao.insert(coursDifferentSession)

        val coursSession3 = Cours(
                "TEST123",
                "01",
                sessionAbrege,
                "7365",
                "B+",
                3,
                "Test"
        )
        dao.insert(coursSession3)

        val fromDb = getValue(dao.getCoursSession(sessionAbrege))
        assertEquals(fromDb.size, 3)
        assertEquals(coursSession1, fromDb[0])
        assertEquals(coursSession2, fromDb[1])
        assertEquals(coursSession3, fromDb[2])
    }
}