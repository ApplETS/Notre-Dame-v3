package ca.etsmtl.applets.repository.data.db.signets

import androidx.test.runner.AndroidJUnit4
import ca.etsmtl.applets.repository.LiveDataTestUtil
import ca.etsmtl.applets.repository.data.db.DbTest
import ca.etsmtl.applets.repository.data.db.dao.signets.EnseignantDao
import ca.etsmtl.applets.repository.data.db.entity.signets.EnseignantEntity
import junit.framework.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by Sonphil on 24-05-18.
 */
@RunWith(AndroidJUnit4::class)
class EnseignantDaoTest : DbTest() {
    private val entity = EnseignantEntity(
            "A-4526",
            "514-396-8800, poste 7810",
            "Oui",
            "Houde",
            "Michel",
            "cc-Michel.HOUDE@etsmtl.ca"
    )
    private lateinit var dao: EnseignantDao

    @Before
    fun setUp() {
        dao = db.enseignantDao()
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
        val same = EnseignantEntity(
                "A-4526",
                "514-396-8800, poste 1234",
                "Non",
                "Houde",
                "Michel",
                "cc-Michel.HOUDE@etsmtl.ca"
        )
        dao.insert(same)
        val fromDb = LiveDataTestUtil.getValue(dao.getAll())
        Assert.assertNotNull(fromDb)
        Assert.assertEquals(same, fromDb[0])
    }
}