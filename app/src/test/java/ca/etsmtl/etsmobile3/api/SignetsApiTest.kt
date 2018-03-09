package ca.etsmtl.etsmobile3.api

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.support.annotation.NonNull
import ca.etsmtl.etsmobile3.data.api.ApiResponse
import ca.etsmtl.etsmobile3.data.api.SignetsApi
import ca.etsmtl.etsmobile3.data.model.Etudiant
import ca.etsmtl.etsmobile3.data.model.UserCredentials
import ca.etsmtl.etsmobile3.util.LiveDataCallAdapterFactory
import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.Okio
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.IOException
import java.nio.charset.StandardCharsets
import java.util.*
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import kotlin.test.assertEquals
import kotlin.test.assertTrue


/**
 * Created by Sonphil on 09-03-18.
 */
@RunWith(JUnit4::class)
class SignetsApiTest {
    companion object {
        // Time out in seconds
        val TIME_OUT = 2
    }

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var mockWebServer: MockWebServer
    private lateinit var api: SignetsApi
    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    @Before
    @Throws(IOException::class)
    fun createService() {
        mockWebServer = MockWebServer()
        api = Retrofit.Builder()
                .baseUrl(mockWebServer.url("/"))
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .addCallAdapterFactory(LiveDataCallAdapterFactory())
                .build()
                .create(SignetsApi::class.java)
    }

    @After
    @Throws(IOException::class)
    fun stopService() {
        mockWebServer.shutdown()
    }

    @Throws(InterruptedException::class)
    private fun getValue(@NonNull liveData: LiveData<ApiResponse<Etudiant>>): ApiResponse<Etudiant> {
        val data = arrayOfNulls<Any>(1)
        val latch = CountDownLatch(1)
        val observer = object : Observer<ApiResponse<Etudiant>> {
            override fun onChanged(response: ApiResponse<Etudiant>?) {
                data[0] = response
                latch.countDown()
                liveData.removeObserver(this)
            }
        }
        liveData.observeForever(observer)
        latch.await(TIME_OUT.toLong(), TimeUnit.SECONDS)
        //noinspection unchecked
        return data[0] as ApiResponse<Etudiant>
    }

    @Test
    @Throws(IOException::class, InterruptedException::class)
    fun getInfoEtudiantNoError() {
        enqueueResponse("info_etudiant_no_error.json")

        val etudiant: Etudiant = getValue(api.infoEtudiant(UserCredentials("AM41234", "test!"))).body!!

        assertEquals("Liu", etudiant.data?.nom)
        assertEquals("Philippe", etudiant.data?.prenom)
        assertEquals("LIUP27099105", etudiant.data?.codePerm)
        assertEquals("0,00\$", etudiant.data?.soldeTotal)
        assertTrue { etudiant.data?.masculin!! }
        val errorStr = etudiant.data?.erreur
        assertTrue { errorStr == null || errorStr.isEmpty() }
    }

    @Test
    @Throws(IOException::class, InterruptedException::class)
    fun getInfoEtudiantError() {
        enqueueResponse("info_etudiant_error.json")

        val etudiant: Etudiant = getValue(api.infoEtudiant(UserCredentials("AM41234", "test!"))).body!!

        assertEquals("", etudiant.data?.nom)
        assertEquals("", etudiant.data?.prenom)
        assertEquals("", etudiant.data?.codePerm)
        assertEquals("", etudiant.data?.soldeTotal)
        val errorStr = etudiant.data?.erreur
        assertEquals("Code d'accès ou mot de passe invalide", errorStr)
    }

    @Throws(IOException::class)
    private fun enqueueResponse(fileName: String) {
        enqueueResponse(fileName, Collections.emptyMap())
    }

    @Throws(IOException::class)
    private fun enqueueResponse(fileName: String, headers: Map<String, String>) {
        val inputStream = javaClass.classLoader.getResourceAsStream("api-response/$fileName")
        val source = Okio.buffer(Okio.source(inputStream))
        val mockResponse = MockResponse()
        for ((key, value) in headers) {
            mockResponse.addHeader(key, value)
        }
        mockWebServer.enqueue(mockResponse
                .setBody(source.readString(StandardCharsets.UTF_8)))
    }
}