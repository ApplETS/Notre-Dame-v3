package ca.etsmtl.applets.repository.data.repository

import model.Cours
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import kotlin.test.assertFalse
import kotlin.test.assertTrue

/**
 * Created by Sonphil on 12-08-18.
 */
@RunWith(JUnit4::class)
class CoursTest {
    val cours = Cours(
            "LOG530",
            "01",
            "H2018",
            "7365",
            "A",
            "79",
            3,
            "Réingénierie du logiciel"
    )

    @Test
    fun testValidSession() {
        assertTrue(cours.apply { session = "E2018" }.hasValidSession())

        assertTrue(cours.apply { session = "É2018" }.hasValidSession())
    }

    @Test
    fun testInvalidSession() {
        assertFalse(cours.apply { session = "s.o." }.hasValidSession())

        assertFalse(cours.apply { session = "B2018" }.hasValidSession())

        assertFalse(cours.apply { session = "c2018" }.hasValidSession())

        assertFalse(cours.apply { session = "Été2018" }.hasValidSession())

        assertFalse(cours.apply { session = "É18" }.hasValidSession())

        assertFalse(cours.apply { session = "a08" }.hasValidSession())

        assertFalse(cours.apply { session = "Automne 2018" }.hasValidSession())
    }
}