package ca.etsmtl.applets.repository.data.db.signets

import android.support.test.runner.AndroidJUnit4
import ca.etsmtl.applets.repository.LiveDataTestUtil.getValue
import ca.etsmtl.applets.repository.data.db.DbTest
import ca.etsmtl.applets.repository.data.db.entity.signets.ProgrammeEntity
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by Sonphil on 17-05-18.
 */
@RunWith(AndroidJUnit4::class)
class ProgrammeDaoTest : DbTest() {

    private val programme0 = ProgrammeEntity(
            "1234",
            "Test0",
            "",
            "actif",
            "A2014",
            "H2015",
            "4,09/4,30",
            0,
            12,
            0,
            0,
            31,
            31,
            0
    )

    @Test
    fun testInsert() {
        val programme1 = ProgrammeEntity(
                "4321",
                "Test1",
                "",
                "actif",
                "A2014",
                "H2015",
                "4,09/4,30",
                0,
                12,
                0,
                0,
                987,
                987,
                0
        )
        db.programmeDao().insert(programme0)
        db.programmeDao().insert(programme1)
        val programmesFromDb = getValue(db.programmeDao().getAll())
        assertNotNull(programmesFromDb)
        assertEquals(2, programmesFromDb.size)
        assertEquals(programme0, programmesFromDb[0])
        assertEquals(programme1, programmesFromDb[1])
    }

    @Test
    fun testInsertSameProgramme() {
        val programme1 = ProgrammeEntity(
                "1234",
                "Test1",
                "",
                "actif",
                "A2014",
                "H2015",
                "4,09/4,30",
                0,
                12,
                0,
                0,
                31,
                31,
                0
        )
        db.programmeDao().insert(programme0)
        db.programmeDao().insert(programme1)
        val programmesFromDb = getValue(db.programmeDao().getAll())
        assertNotNull(programmesFromDb)
        assertEquals(1, programmesFromDb.size)
        assertEquals(programme1, programmesFromDb[0])
    }

    @Test
    fun testInsertMultipleProgrammesAtOnce() {
        val programme1 = ProgrammeEntity(
                "4321",
                "Test1",
                "",
                "actif",
                "A2014",
                "H2015",
                "4,09/4,30",
                0,
                12,
                0,
                0,
                987,
                987,
                0
        )
        val programme2 = ProgrammeEntity(
                "4321",
                "Test2",
                "",
                "actif",
                "A2014",
                "H2015",
                "4,09/4,30",
                0,
                12,
                0,
                0,
                987,
                987,
                0
        )
        val programmes = listOf(programme0, programme1, programme2)
        db.programmeDao().insertAll(programmes)
        val programmesFromDb = getValue(db.programmeDao().getAll())
        assertNotNull(programmesFromDb)
        assertEquals(2, programmesFromDb.size)
        assertEquals(programme0, programmesFromDb[0])
        assertEquals(programme2, programmesFromDb[1])
    }

    @Test
    fun testDelete() {
        val programme1 = ProgrammeEntity(
                "4321",
                "Test1",
                "",
                "actif",
                "A2014",
                "H2015",
                "4,09/4,30",
                0,
                12,
                0,
                0,
                987,
                987,
                0
        )
        db.programmeDao().insert(programme0)
        db.programmeDao().insert(programme1)
        var programmesFromDb = getValue(db.programmeDao().getAll())
        assertEquals(2, programmesFromDb.size)
        db.programmeDao().deleteAll()
        programmesFromDb = getValue(db.programmeDao().getAll())
        assertEquals(0, programmesFromDb.size)
    }
}