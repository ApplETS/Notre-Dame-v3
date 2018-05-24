package ca.etsmtl.etsmobile.data.db.signets

import android.support.test.runner.AndroidJUnit4
import ca.etsmtl.etsmobile.LiveDataTestUtil
import ca.etsmtl.etsmobile.data.db.DbTest
import ca.etsmtl.etsmobile.data.db.dao.EnseignantDao
import ca.etsmtl.etsmobile.data.model.signets.Enseignant
import junit.framework.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by Sonphil on 24-05-18.
 */
@RunWith(AndroidJUnit4::class)
class EnseignantDaoTest : DbTest() {
    private val entity = Enseignant(
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
        val same = Enseignant(
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