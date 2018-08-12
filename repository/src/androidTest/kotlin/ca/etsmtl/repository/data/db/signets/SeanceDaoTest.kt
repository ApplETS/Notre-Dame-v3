package ca.etsmtl.repository.data.db.signets

import ca.etsmtl.repository.LiveDataTestUtil
import ca.etsmtl.repository.data.db.DbTest
import ca.etsmtl.repository.data.db.dao.signets.SeanceDao
import ca.etsmtl.repository.data.db.entity.signets.SeanceEntity
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import org.junit.Before
import org.junit.Test

/**
 * Created by Sonphil on 02-06-18.
 */
class SeanceDaoTest : DbTest() {
    private val entity = SeanceEntity(
            "/Date(1525093200000)/",
            "/Date(1525105800000)/",
            "Cours",
            "B-0904",
            "Activité de cours",
            "Algèbre linéaire et géométrie de l'espace",
            "MAT472",
            "E2018"
    )
    private lateinit var dao: SeanceDao

    @Before
    fun setUp() {
        dao = db.seanceDao()
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
        val same = SeanceEntity(
                "/Date(1525093200000)/",
                "/Date(1527950896000)/",
                "Cours",
                "B-0904",
                "Activité de cours",
                "Algèbre linéaire et géométrie de l'espace",
                "MAT472",
                "E2018"
        )
        dao.insert(same)
        val fromDb = LiveDataTestUtil.getValue(dao.getAll())
        assertEquals(same, fromDb[0])
    }

    @Test
    fun testByCoursAndSession() {
        val expected = SeanceEntity(
                "/Date(1525093200123)/",
                "/Date(1527950896022)/",
                "Cours",
                "B-0904",
                "Activité de cours",
                "Algèbre linéaire et géométrie de l'espace",
                "MAT472",
                "A2018"
        )
        dao.insert(expected)

        val unexpected1 = SeanceEntity(
                "/Date(1525093200000)/",
                "/Date(1527950896000)/",
                "Cours",
                "B-0904",
                "Activité de cours",
                "Foo",
                "LOG123",
                expected.session
        )
        dao.insert(unexpected1)

        val unexpected2 = SeanceEntity(
                "/Date(1525093200000)/",
                "/Date(1527950896000)/",
                "Cours",
                "B-0904",
                "Activité de cours",
                "Foo",
                expected.sigleCours,
                "E2018"
        )
        dao.insert(unexpected2)

        val seances = LiveDataTestUtil.getValue(dao.getByCoursAndSession(
                expected.sigleCours,
                expected.session
        ))
        assertEquals(1, seances.size)
        assertEquals(expected, seances[0])
    }

    @Test
    fun testDeleteByCoursAndSession() {
        dao.deleteByCoursAndSession(entity.sigleCours, "A2018")
        assertEquals(1, LiveDataTestUtil.getValue(dao.getAll()).size)

        dao.deleteByCoursAndSession("LOG123", entity.session)
        assertEquals(1, LiveDataTestUtil.getValue(dao.getAll()).size)

        dao.deleteByCoursAndSession(entity.sigleCours, entity.session)
        assertEquals(0, LiveDataTestUtil.getValue(dao.getAll()).size)
    }
}