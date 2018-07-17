package ca.etsmtl.repository.data.db.signets

import ca.etsmtl.repository.LiveDataTestUtil
import ca.etsmtl.repository.data.db.DbTest
import ca.etsmtl.repository.data.db.dao.signets.SeanceDao
import ca.etsmtl.repository.data.db.entity.signets.CoursEntity
import ca.etsmtl.repository.data.db.entity.signets.SeanceEntity
import junit.framework.Assert
import org.junit.Before
import org.junit.Test

/**
 * Created by Sonphil on 02-06-18.
 */
class SeanceDaoTest : DbTest() {
    private val entity = SeanceEntity(
            "/Date(1525093200000)/",
            "/Date(1525105800000)/",
            CoursEntity(
                    "MAT472",
                    "02",
                    "E2018",
                    "foo",
                    "B+",
                    4,
                    "Algèbre linéaire et géométrie de l'espace"
            ),
            "Cours",
            "B-0904",
            "Activité de cours"
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
        Assert.assertNotNull(fromDb)
        Assert.assertEquals(entity, fromDb[0])
    }

    @Test
    fun testInsertSame() {
        val same = SeanceEntity(
                "/Date(1525093200000)/",
                "/Date(1527950896000)/",
                CoursEntity(
                        "MAT472",
                        "02",
                        "É2018",
                        "foo",
                        "B+",
                        4,
                        "Algèbre linéaire et géométrie de l'espace"
                ),
                "Cours",
                "B-0904",
                "Activité de cours"
        )
        dao.insert(same)
        val fromDb = LiveDataTestUtil.getValue(dao.getAll())
        Assert.assertEquals(same, fromDb[0])
    }
}