package ca.etsmtl.applets.repository.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import ca.etsmtl.applets.repository.data.model.Resource
import ca.etsmtl.applets.repository.util.zipResourceTo
import ca.etsmtl.applets.repository.util.zipTo
import com.nhaarman.mockito_kotlin.capture
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.MockitoAnnotations
import kotlin.random.Random
import kotlin.test.assertEquals
import kotlin.test.assertNull

/**
 * Created by Sonphil on 03-11-18.
 */

class LiveDataExtTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()
    @Captor
    private lateinit var pairArgumentCaptor: ArgumentCaptor<Pair<String?, Int?>>
    @Captor
    private lateinit var resArgumentCaptor: ArgumentCaptor<Resource<Pair<String?, Int?>>>

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun testZip() {
        val mutableLiveDataA = MutableLiveData<String?>()
        val mutableLiveDataB = MutableLiveData<Int?>()
        val observer = mock<Observer<Pair<String?, Int?>>>()
        val zip = mutableLiveDataA zipTo mutableLiveDataB
        zip.observeForever(observer)

        val expectedString = "Test"
        mutableLiveDataA.value = expectedString
        verify(observer).onChanged(capture(pairArgumentCaptor))
        assertEquals(expectedString, pairArgumentCaptor.value.first)
        assertEquals(null, pairArgumentCaptor.value.second)

        var expectedInt: Int? = null
        mutableLiveDataB.value = expectedInt
        verify(observer, times(2)).onChanged(capture(pairArgumentCaptor))
        assertEquals(expectedString, pairArgumentCaptor.value.first)
        assertEquals(expectedInt, pairArgumentCaptor.value.second)

        expectedInt = Random.nextInt()
        mutableLiveDataB.value = expectedInt
        verify(observer, times(3)).onChanged(capture(pairArgumentCaptor))
        assertEquals(expectedString, pairArgumentCaptor.value.first)
        assertEquals(expectedInt, pairArgumentCaptor.value.second)
    }

    @Test
    fun testZipResourceNull() {
        val mutableLiveDataA = MutableLiveData<Resource<String>>()
        val mutableLiveDataB = MutableLiveData<Resource<Int>>()
        val observer = mock<Observer<Resource<Pair<String?, Int?>>>>()
        val zip = mutableLiveDataA zipResourceTo mutableLiveDataB

        zip.observeForever(observer)
        verify(observer).onChanged(capture(resArgumentCaptor))
        assertEquals(Resource.Status.LOADING, resArgumentCaptor.value.status)
    }

    @Test
    fun testZipResourceError() {
        val mutableLiveDataA = MutableLiveData<Resource<String>>()
        val mutableLiveDataB = MutableLiveData<Resource<Int>>()
        val observer = mock<Observer<Resource<Pair<String?, Int?>>>>()
        val zip = mutableLiveDataA zipResourceTo mutableLiveDataB
        zip.observeForever(observer)

        mutableLiveDataA.value = Resource.error("Error", null)
        verify(observer, times(2)).onChanged(capture(resArgumentCaptor))
        assertEquals(Resource.Status.LOADING, resArgumentCaptor.value.status)
        assertNull(resArgumentCaptor.value.data?.first)
        assertNull(resArgumentCaptor.value.data?.second)

        mutableLiveDataB.value = Resource.error("Error", null)
        verify(observer, times(3)).onChanged(capture(resArgumentCaptor))
        assertEquals(Resource.Status.ERROR, resArgumentCaptor.value.status)
        assertNull(resArgumentCaptor.value.data?.first)
        assertNull(resArgumentCaptor.value.data?.second)

        val expectedData = "Foo"
        mutableLiveDataA.value = Resource.error("Error", expectedData)
        verify(observer, times(4)).onChanged(capture(resArgumentCaptor))
        assertEquals(Resource.Status.ERROR, resArgumentCaptor.value.status)
        assertEquals(expectedData, resArgumentCaptor.value.data?.first)
        assertNull(resArgumentCaptor.value.data?.second)
    }

    @Test
    fun testZipResourceLoading() {
        val mutableLiveDataA = MutableLiveData<Resource<String>>()
        val mutableLiveDataB = MutableLiveData<Resource<Int>>()
        val observer = mock<Observer<Resource<Pair<String?, Int?>>>>()
        val zip = mutableLiveDataA zipResourceTo mutableLiveDataB

        zip.observeForever(observer)
        verify(observer).onChanged(capture(resArgumentCaptor))
        assertEquals(Resource.Status.LOADING, resArgumentCaptor.value.status)

        var expectedDataA = "dataA"
        var expectedDataB: Int? = null
        mutableLiveDataA.value = Resource.loading(expectedDataA)
        verify(observer, times(2)).onChanged(capture(resArgumentCaptor))
        assertEquals(Resource.Status.LOADING, resArgumentCaptor.value.status)
        assertEquals(expectedDataA, resArgumentCaptor.value.data?.first)
        assertEquals(expectedDataB, resArgumentCaptor.value.data?.second)

        expectedDataA = "dataA"
        expectedDataB = 3
        mutableLiveDataB.value = Resource.loading(expectedDataB)
        verify(observer, times(3)).onChanged(capture(resArgumentCaptor))
        assertEquals(Resource.Status.LOADING, resArgumentCaptor.value.status)
        assertEquals(expectedDataA, resArgumentCaptor.value.data?.first)
        assertEquals(expectedDataB, resArgumentCaptor.value.data?.second)

        expectedDataA = "newDataA"
        mutableLiveDataA.value = Resource.success(expectedDataA)
        verify(observer, times(4)).onChanged(capture(resArgumentCaptor))
        assertEquals(Resource.Status.LOADING, resArgumentCaptor.value.status)
        assertEquals(expectedDataA, resArgumentCaptor.value.data?.first)
        assertEquals(expectedDataB, resArgumentCaptor.value.data?.second)

        expectedDataB++
        mutableLiveDataB.value = Resource.success(expectedDataB)
        verify(observer, times(5)).onChanged(capture(resArgumentCaptor))
        assertEquals(Resource.Status.SUCCESS, resArgumentCaptor.value.status)
        assertEquals(expectedDataA, resArgumentCaptor.value.data?.first)
        assertEquals(expectedDataB, resArgumentCaptor.value.data?.second)
    }
}