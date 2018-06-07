package ca.etsmtl.repos.data.db.signets

import android.support.test.runner.AndroidJUnit4
import ca.etsmtl.repos.LiveDataTestUtil.getValue
import ca.etsmtl.repos.data.db.DbTest
import ca.etsmtl.repos.data.model.signets.Etudiant
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by Sonphil on 22-04-18.
 */
@RunWith(AndroidJUnit4::class)
class EtudiantDaoTest : DbTest() {
    @Test
    fun testInsertEtudiant() {
        val etudiant = Etudiant("fooType", "Luu", "Phil", "LUUP12345678", "123,45$", true, "")
        db.etudiantDao().insert(etudiant)
        val etudiantFromDb = getValue(db.etudiantDao().getAll())
        assertNotNull(etudiantFromDb)
        assertEquals(etudiant, etudiantFromDb[0])
    }

    @Test
    fun testInsertSameEtudiant() {
        val etudiant = Etudiant("fooType", "Luu", "Phil", "LUUP12345678", "123,45$", true, "")
        db.etudiantDao().insert(etudiant)
        val sameEtudiant = Etudiant("fooType", "Luu", "Phil", "LUUP12345678", "999,45$", true, "")
        db.etudiantDao().insert(sameEtudiant)
        val etudiantFromDb = getValue(db.etudiantDao().getAll())
        assertNotNull(etudiantFromDb)
        assertEquals(sameEtudiant, etudiantFromDb[0])
    }

    @Test
    fun testDelete() {
        val etudiant = Etudiant("fooType", "Luu", "Phil", "LUUP12345678", "123,45$", true, "")
        db.etudiantDao().insert(etudiant)
        var etudiantFromDb = getValue(db.etudiantDao().getAll())
        assertEquals(1, etudiantFromDb.size)

        db.etudiantDao().deleteAll()
        etudiantFromDb = getValue(db.etudiantDao().getAll())
        assertEquals(0, etudiantFromDb.size)
    }
}