package ca.etsmtl.applets.etsmobile.extension

import ca.etsmtl.applets.etsmobile.R
import ca.etsmtl.applets.etsmobile.presentation.App
import com.nhaarman.mockitokotlin2.mock
import model.Cours
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito
import kotlin.test.assertEquals

/**
 * Created by Sonphil on 03-03-19.
 */

@RunWith(JUnit4::class)
class CoursExtTest {
    private val app: App = mock()

    @Before
    fun setup() {
        Mockito.`when`(app.getString(R.string.abbreviation_not_available)).thenReturn("N/A")
    }

    @Test
    fun cote_NotNullOrEmpty_ReturnsCote() {
        // given
        val cours = Cours(
            "MAT123",
            "01",
            "s.o",
            "7365",
            "K",
            "91",
            3,
            "Math"
        )

        // when
        val displayableCote = cours.getDisplayableCote(app)

        // then
        assertEquals(cours.cote, displayableCote)
    }

    @Test
    fun cote_CoteEmptyAndNoteSur100NotNullOrEmpty_ReturnsNoteSur100() {
        // given
        val cours = Cours(
            "MAT123",
            "01",
            "s.o",
            "7365",
            "",
            "91",
            3,
            "Math"
        )
        Mockito.`when`(app.getString(R.string.text_grade_in_percentage)).thenReturn("%1\$s %%")

        // when
        val displayableCote = cours.getDisplayableCote(app)

        // then
        assertEquals(cours.noteSur100 + " %", displayableCote)
    }

    @Test
    fun cote_CoteEmptyAndNoteSur100NullOrEmpty_ReturnsNotAvailable() {
        // given
        val cours = Cours(
            "MAT123",
            "01",
            "s.o",
            "7365",
            "",
            "",
            3,
            "Math"
        )
        Mockito.`when`(app.getString(R.string.abbreviation_not_available)).thenReturn("N/A")

        // when
        val displayableCote = cours.getDisplayableCote(app)

        // then
        assertEquals("N/A", displayableCote)
    }
}