package ca.etsmtl.applets.repository.data

import model.UniversalCode
import org.junit.Before
import org.junit.Test
import org.mockito.MockitoAnnotations
import kotlin.test.assertEquals
import kotlin.test.assertNull

/**
 * Created by Sonphil on 30-12-18.
 */

class UniversalCodeTest {
    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun universalCode_Empty_EmptyError() {
        assertEquals(UniversalCode.Error.EMPTY, UniversalCode("").error)
    }

    @Test
    fun universalCode_MissingOneDigit_InvalidError() {
        assertEquals(UniversalCode.Error.INVALID, UniversalCode("AZ1234").error)
    }

    @Test
    fun universalCode_MissingOneLetter_InvalidError() {
        assertEquals(UniversalCode.Error.INVALID, UniversalCode("Z12345").error)
    }

    @Test
    fun universalCode_HasAtens_InvalidError() {
        assertEquals(UniversalCode.Error.INVALID, UniversalCode("AZ12345@ens").error)
    }

    @Test
    fun universalCode_PrependedByBackslashes_InvalidError() {
        assertEquals(UniversalCode.Error.INVALID, UniversalCode("\\AZ12345").error)
    }

    @Test
    fun universalCode_Valid_NullError() {
        assertNull(UniversalCode("AZ12345").error)
    }
}