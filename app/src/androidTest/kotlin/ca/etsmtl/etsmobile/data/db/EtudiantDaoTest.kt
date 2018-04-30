package ca.etsmtl.etsmobile.data.db

import android.support.test.runner.AndroidJUnit4
import ca.etsmtl.etsmobile.data.model.signets.Etudiant
import ca.etsmtl.etsmobile.LiveDataTestUtil.getValue
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertFalse
import junit.framework.Assert.assertNotNull
import junit.framework.Assert.assertNull
import junit.framework.Assert.assertTrue
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
        db.etudiantDao().insertEtudiant(etudiant)
        val etudiantFromDb = getValue(db.etudiantDao().getEtudiant())
        assertNotNull(etudiantFromDb)
        assertEquals("fooType", etudiantFromDb.type)
        assertEquals("Luu", etudiantFromDb.n)
        assertEquals(etudiantFromDb.p, "Phil")
        assertEquals("LUUP12345678", etudiantFromDb.codePerm)
        assertEquals("123,45$", etudiantFromDb.soldeTotal)
        assertTrue(etudiantFromDb.masculin!!)
        assertFalse(!etudiantFromDb.masculin!!)
        assertEquals("", etudiantFromDb.erreur)
    }

    @Test
    fun testInsertSameEtudiant() {
        val etudiant = Etudiant("fooType", "Luu", "Phil", "LUUP12345678", "123,45$", true, "")
        db.etudiantDao().insertEtudiant(etudiant)
        val sameEtudiant = Etudiant("fooType", "Luu", "Phil", "LUUP12345678", "999,45$", true, "")
        db.etudiantDao().insertEtudiant(sameEtudiant)
        val etudiantFromDb = getValue(db.etudiantDao().getEtudiant())
        assertNotNull(etudiantFromDb)
        assertEquals("fooType", etudiantFromDb.type)
        assertEquals("Luu", etudiantFromDb.n)
        assertEquals(etudiantFromDb.p, "Phil")
        assertEquals("LUUP12345678", etudiantFromDb.codePerm)
        assertEquals("999,45$", etudiantFromDb.soldeTotal)
        assertTrue(etudiantFromDb.masculin!!)
        assertFalse(!etudiantFromDb.masculin!!)
        assertEquals("", etudiantFromDb.erreur)
    }

    @Test
    fun testDelete() {
        val etudiant = Etudiant("fooType", "Luu", "Phil", "LUUP12345678", "123,45$", true, "")
        db.etudiantDao().insertEtudiant(etudiant)
        var etudiantFromDb = getValue(db.etudiantDao().getEtudiant())
        assertNotNull(etudiantFromDb)

        db.etudiantDao().deleteAll()
        etudiantFromDb = getValue(db.etudiantDao().getEtudiant())
        assertNull(etudiantFromDb)
    }
}