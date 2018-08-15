package ca.etsmtl.repository.data.api

import ca.etsmtl.repository.data.api.response.signets.ApiCours
import junit.framework.Assert.assertFalse
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import kotlin.test.assertTrue

/**
 * Created by Sonphil on 12-08-18.
 */
@RunWith(JUnit4::class)
class ApiCoursTest {
    val apiCours = ApiCours(
            "LOG530",
            "01",
            "H2018",
            "7365",
            "A",
            3,
            "Réingénierie du logiciel"
    )

    @Test
    fun testValidSession() {
        assertTrue(apiCours.apply { session = "E2018" }.hasValidSession())

        assertTrue(apiCours.apply { session = "É2018" }.hasValidSession())
    }

    @Test
    fun testInvalidSession() {
        assertFalse(apiCours.apply { session = "s.o." }.hasValidSession())

        assertFalse(apiCours.apply { session = "B2018" }.hasValidSession())

        assertFalse(apiCours.apply { session = "c2018" }.hasValidSession())

        assertFalse(apiCours.apply { session = "Été2018" }.hasValidSession())

        assertFalse(apiCours.apply { session = "É18" }.hasValidSession())

        assertFalse(apiCours.apply { session = "a08" }.hasValidSession())

        assertFalse(apiCours.apply { session = "Automne 2018" }.hasValidSession())
    }
}