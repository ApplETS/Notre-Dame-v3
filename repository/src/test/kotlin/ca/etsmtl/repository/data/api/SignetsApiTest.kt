package ca.etsmtl.repository.data.api

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import android.support.annotation.NonNull
import ca.etsmtl.repository.data.api.response.mapper.ApplicationJsonAdapterFactory
import ca.etsmtl.repository.data.api.response.signets.ApiActivite
import ca.etsmtl.repository.data.api.response.signets.ApiEnseignant
import ca.etsmtl.repository.data.api.response.signets.ApiEtudiant
import ca.etsmtl.repository.data.api.response.signets.ApiEvaluation
import ca.etsmtl.repository.data.api.response.signets.ApiHoraireExamenFinal
import ca.etsmtl.repository.data.api.response.signets.ApiJourRemplace
import ca.etsmtl.repository.data.api.response.signets.ApiListeDeCours
import ca.etsmtl.repository.data.api.response.signets.ApiListeDeSessions
import ca.etsmtl.repository.data.api.response.signets.ApiListeDesActivitesEtProf
import ca.etsmtl.repository.data.api.response.signets.ApiListeDesElementsEvaluation
import ca.etsmtl.repository.data.api.response.signets.ApiListeDesSeances
import ca.etsmtl.repository.data.api.response.signets.ApiListeHoraireExamensFinaux
import ca.etsmtl.repository.data.api.response.signets.ApiListeJoursRemplaces
import ca.etsmtl.repository.data.api.response.signets.ApiListeProgrammes
import ca.etsmtl.repository.data.api.response.signets.ApiSeance
import ca.etsmtl.repository.data.api.response.signets.ApiSession
import ca.etsmtl.repository.data.api.response.signets.ApiSignetsData
import ca.etsmtl.repository.data.api.response.signets.ApiSignetsModel
import ca.etsmtl.repository.data.model.SignetsUserCredentials
import ca.etsmtl.repository.util.LiveDataCallAdapterFactory
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
import java.util.Collections
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
    private val moshi = Moshi.Builder().add(ApplicationJsonAdapterFactory.INSTANCE).build()

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
    private inline fun <reified T : ApiSignetsData> getValue(@NonNull liveData: LiveData<ApiResponse<ApiSignetsModel<T>>>): ApiResponse<ApiSignetsModel<T>> {
        val data = arrayOfNulls<Any>(1)
        val latch = CountDownLatch(1)
        val observer = object : Observer<ApiResponse<ApiSignetsModel<T>>> {
            override fun onChanged(response: ApiResponse<ApiSignetsModel<T>>?) {
                data[0] = response
                latch.countDown()
                liveData.removeObserver(this)
            }
        }
        liveData.observeForever(observer)
        latch.await(TIME_OUT.toLong(), TimeUnit.SECONDS)
        //noinspection unchecked
        return data[0] as ApiResponse<ApiSignetsModel<T>>
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

    @Test
    @Throws(IOException::class, InterruptedException::class)
    fun testGetListeProgrammesNoError() {
        enqueueResponse("liste_programmes_no_error.json")

        val apiResponse = api.listeProgrammes(SignetsUserCredentials(
                "AM41234",
                "test!"
        ))
        val wrapper: ApiSignetsModel<ApiListeProgrammes> = getValue(apiResponse).body!!
        val errorStr = wrapper.data?.erreur
        assertTrue { errorStr == null || errorStr.isEmpty() }

        val listeProgrammes: ApiListeProgrammes = wrapper.data!!
        val programmes = listeProgrammes.liste!!
        val p0 = programmes[0]!!
        assertEquals("0725", p0.code)
        assertEquals("Microprogramme de 1er cycle en enseignement coopératif I",
                p0.libelle)
        assertEquals("", p0.profil)
        assertEquals("diplômé", p0.statut)
        assertEquals("É2016", p0.sessionDebut)
        assertEquals("É2016", p0.sessionFin)
        assertEquals("", p0.moyenne)
        assertEquals(0, p0.nbEquivalences)
        assertEquals(1, p0.nbCrsReussis)
        assertEquals(0, p0.nbCrsEchoues)
        assertEquals(0, p0.nbCreditsInscrits)
        assertEquals(9, p0.nbCreditsCompletes)
        assertEquals(9, p0.nbCreditsPotentiels)
        assertEquals(0, p0.nbCreditsRecherche)
        assertEquals("SignetsPourEtudiants.SignetsMobile+listeDesProgrammes",
                wrapper.data!!.type)
    }

    @Test
    @Throws(IOException::class, InterruptedException::class)
    fun testGetListeProgrammesError() {
        enqueueResponse("liste_programmes_error.json")

        val apiResponse = api.listeProgrammes(SignetsUserCredentials(
                "AM41234",
                "test!"
        ))
        val wrapper: ApiSignetsModel<ApiListeProgrammes> = getValue(apiResponse).body!!
        val errorStr = wrapper.data!!.erreur
        assertEquals("Code d'accès ou mot de passe invalide", errorStr)
        assertEquals(
                "SignetsPourEtudiants.SignetsMobile+listeDesProgrammes",
                wrapper.data!!.type
        )
        assertEquals(0, wrapper.data!!.liste!!.size)
    }

    @Test
    @Throws(IOException::class, InterruptedException::class)
    fun testGetListeHoraireExamensFinauxNoError() {
        enqueueResponse("liste_horaire_examens_finaux_no_error.json")

        val apiResponse = api.listeHoraireExamensFinaux(
                "AM41234",
                "test!",
                "É2018"
        )
        val wrapper: ApiSignetsModel<ApiListeHoraireExamensFinaux> = getValue(apiResponse).body!!
        val errorStr = wrapper.data!!.erreur
        assertTrue { errorStr == null || errorStr.isEmpty() }
        val listeHoraireExamensFinaux = wrapper.data!!
        assertEquals(4, listeHoraireExamensFinaux.listeHoraire!!.size)
        val horaireExamenFinal0: ApiHoraireExamenFinal = listeHoraireExamensFinaux.listeHoraire!![0]!!
        assertEquals("GPE450", horaireExamenFinal0.sigle)
        assertEquals("01", horaireExamenFinal0.groupe)
        assertEquals("2018-04-13", horaireExamenFinal0.dateExamen)
        assertEquals("13:30", horaireExamenFinal0.heureDebut)
        assertEquals("16:30", horaireExamenFinal0.heureFin)
        assertEquals("A-1302", horaireExamenFinal0.local)
        assertEquals("SignetsPourEtudiants.SignetsMobile+listeHoraireExamensFinaux",
                wrapper.data!!.type)
    }

    @Test
    @Throws(IOException::class, InterruptedException::class)
    fun testGetListeHoraireExamensFinauxError() {
        enqueueResponse("liste_horaire_examens_finaux_error.json")
        val apiResponse = api.listeHoraireExamensFinaux(
                "AM41234",
                "test!",
                "É2018"
        )
        val wrapper: ApiSignetsModel<ApiListeHoraireExamensFinaux> = getValue(apiResponse).body!!
        val errorStr = wrapper.data!!.erreur
        assertEquals("Code d'accès ou mot de passe invalide", errorStr)
        assertEquals(
                "SignetsPourEtudiants.SignetsMobile+listeHoraireExamensFinaux",
                wrapper.data!!.type
        )
    }

    @Test
    @Throws(IOException::class, InterruptedException::class)
    fun testGetListeCoursIntervalleSessionsNoError() {
        enqueueResponse("liste_cours_intervalle_sessions_no_error.json")
        val wrapper: ApiSignetsModel<ApiListeDeCours> = getValue(api
                .listeCoursIntervalleSessions(
                        "AM41234",
                        "test!",
                        "H2018",
                        "E2018"
                ))
                .body!!
        val errorStr = wrapper.data!!.erreur
        assertTrue { errorStr == null || errorStr.isEmpty() }
        val listeDeCours = wrapper.data!!
        assertEquals(6, listeDeCours.liste!!.size)
        with(listeDeCours.liste!![0]) {
            assertEquals("GPE450", this.sigle)
            assertEquals("01", this.groupe)
            assertEquals("H2018", this.session)
            assertEquals("7365", this.programmeEtudes)
            assertEquals("A+", this.cote)
            assertEquals(3, this.nbCredits)
            assertEquals("Gestion du personnel et relations industrielles", this.titreCours)
        }
        assertEquals("SignetsPourEtudiants.SignetsMobile+ListeDeCours", listeDeCours.type)
    }

    @Test
    @Throws(IOException::class, InterruptedException::class)
    fun testGetListeCoursIntervalleSessionsError() {
        enqueueResponse("liste_cours_intervalle_sessions_error.json")
        val apiResponse = api.listeCoursIntervalleSessions(
                "AM41234",
                "test!",
                "H2018t",
                "E2018"
        )
        val wrapper: ApiSignetsModel<ApiListeDeCours> = getValue(apiResponse).body!!
        val errorStr = wrapper.data!!.erreur
        assertEquals(
                "Session de début invalide:H2018g. L'année doit avoir 4 chiffres.",
                errorStr
        )
    }

    @Test
    @Throws(IOException::class, InterruptedException::class)
    fun testGetListeDesActivitesEtProfNoError() {
        enqueueResponse("liste_horaire_et_prof_no_error.json")
        val apiResponse = api.listeDesActivitesEtProf(
                "AM41234",
                "foo",
                "A2016"
        )
        val wrapper: ApiSignetsModel<ApiListeDesActivitesEtProf> = getValue(apiResponse).body!!
        val errorStr = wrapper.data!!.erreur
        assertTrue { errorStr == null || errorStr.isEmpty() }
        val activites = wrapper.data!!.listeActivites!!
        val enseignants = wrapper.data!!.listeEnseignants!!
        assertEquals(10, activites.size)
        assertEquals(4, enseignants.size)
        val expectedActivite = ApiActivite(
                "LOG210",
                "02",
                1,
                "Lundi",
                "C",
                "Activité de cours",
                "Oui",
                "08:45",
                "12:15",
                "A-1302",
                "Analyse et conception de logiciels"
        )
        assertEquals(expectedActivite, activites[0])
        val expectedEnseignant = ApiEnseignant(
                "A-4526",
                "514-396-8800, poste 7810",
                "Oui",
                "Houde",
                "Michel",
                "cc-Michel.HOUDE@etsmtl.ca"
        )
        assertEquals(expectedEnseignant, enseignants[0])
        assertEquals(
                "SignetsPourEtudiants.SignetsMobile+listeDesActivitesEtProf",
                wrapper.data!!.type
        )
    }

    @Test
    @Throws(IOException::class, InterruptedException::class)
    fun testGetListeDesActivitesEtProfError() {
        enqueueResponse("liste_horaire_et_prof_error.json")
        val apiResponse = api.listeDesActivitesEtProf(
                "AM41234",
                "foo",
                "42016"
        )
        val wrapper: ApiSignetsModel<ApiListeDesActivitesEtProf> = getValue(apiResponse).body!!
        val errorStr = wrapper.data!!.erreur
        assertEquals("Session invalide: 42016", errorStr)
        assertEquals(
                "SignetsPourEtudiants.SignetsMobile+listeDesActivitesEtProf",
                wrapper.data!!.type
        )
    }

    @Test
    @Throws(IOException::class, InterruptedException::class)
    fun testGetInfoEtudiantNoError() {
        enqueueResponse("info_etudiant_no_error.json")

        val apiResponse = api
                .infoEtudiant(SignetsUserCredentials("AM41234", "test!"))
        val etudiantWrapper: ApiSignetsModel<ApiEtudiant> = getValue(apiResponse).body!!
        val etudiant = ApiEtudiant(
                "SignetsPourEtudiants.SignetsMobile+Etudiant",
                "Liu                                     ",
                "Philippe            ",
                "LIUP27099105",
                "0,00\$",
                true,
                ""
        )
        assertEquals(etudiant, etudiantWrapper.data!!)
        val errorStr = etudiantWrapper.data!!.erreur
        assertTrue { errorStr == null || errorStr.isEmpty() }
    }

    @Test
    @Throws(IOException::class, InterruptedException::class)
    fun testGetInfoEtudiantError() {
        enqueueResponse("info_etudiant_error.json")

        val apiResponse = api.infoEtudiant(SignetsUserCredentials(
                "AM41234",
                "test!"
        ))
        val etudiantWrapper: ApiSignetsModel<ApiEtudiant> = getValue(apiResponse).body!!

        assertEquals("", etudiantWrapper.data?.untrimmedNom)
        assertEquals("", etudiantWrapper.data?.untrimmedPrenom)
        assertEquals("", etudiantWrapper.data?.codePerm)
        assertEquals("", etudiantWrapper.data?.soldeTotal)
        val errorStr = etudiantWrapper.data!!.erreur
        assertEquals("Code d'accès ou mot de passe invalide", errorStr)
    }

    @Test
    @Throws(IOException::class, InterruptedException::class)
    fun testGetListeJoursRemplacesNoError() {
        enqueueResponse("liste_jours_remplaces_no_error.json")

        val apiResponse = api.listeJoursRemplaces("É2016")
        val wrapper: ApiSignetsModel<ApiListeJoursRemplaces> = getValue(apiResponse).body!!
        val errorStr = wrapper.data!!.erreur
        assertTrue { errorStr == null || errorStr.isEmpty() }
        val listeJours = wrapper.data!!.listeJours!!
        assertEquals(2, listeJours.size)
        val expectedJourRemplace0 = ApiJourRemplace(
                "2016-05-23",
                "2016-05-25",
                "Journée nationale des Patriotes    "
        )
        assertEquals(expectedJourRemplace0, listeJours[0])
        val expectedJourRemplace1 = ApiJourRemplace(
                "2016-06-24",
                "2016-06-23",
                "Fête nationale du Québec           "
        )
        assertEquals(expectedJourRemplace1, listeJours[1])
        assertEquals(
                "SignetsPourEtudiants.SignetsMobile+listeJoursRemplaces",
                wrapper.data!!.type
        )
    }

    @Test
    @Throws(IOException::class, InterruptedException::class)
    fun testGetListeJoursRemplacesError() {
        enqueueResponse("liste_jours_remplaces_error.json")

        val apiResponse = api.listeJoursRemplaces("É2016")
        val wrapper: ApiSignetsModel<ApiListeJoursRemplaces> = getValue(apiResponse).body!!
        val errorStr = wrapper.data!!.erreur
        assertEquals("Session invalide: É2016e", errorStr)
        assertEquals(
                "SignetsPourEtudiants.SignetsMobile+listeJoursRemplaces",
                wrapper.data!!.type
        )
    }

    @Test
    @Throws(IOException::class, InterruptedException::class)
    fun testGetListeElementsEvaluationNoError() {
        enqueueResponse("liste_elements_evaluation_no_error.json")

        val apiResponse = api.listeDesElementsEvaluation(
                "AM12345",
                "test",
                "INF111",
                "01",
                "H2015"
        )
        val wrapper: ApiSignetsModel<ApiListeDesElementsEvaluation> = getValue(apiResponse).body!!
        val listeDesElementsEvaluation = wrapper.data!!
        val errorStr = listeDesElementsEvaluation.erreur
        assertTrue { errorStr == null || errorStr.isEmpty() }

        val type = wrapper.data!!.type
        assertEquals("SignetsPourEtudiants.SignetsMobile+ListeDesElementsEvaluation", type)
        assertEquals("94,6", listeDesElementsEvaluation.noteACeJour)
        assertEquals("94,6", listeDesElementsEvaluation.scoreFinalSur100)
        assertEquals("65,9", listeDesElementsEvaluation.moyenneClasse)
        assertEquals("18,2", listeDesElementsEvaluation.ecartTypeClasse)
        assertEquals("70,6", listeDesElementsEvaluation.medianeClasse)
        assertEquals("99,0", listeDesElementsEvaluation.rangCentileClasse)
        assertEquals("96,4", listeDesElementsEvaluation.noteACeJourElementsIndividuels)
        assertEquals("65,5", listeDesElementsEvaluation.noteSur100PourElementsIndividuels)

        assertEquals(6, listeDesElementsEvaluation.liste!!.size)

        val expectedEvaluation = ApiEvaluation(
                "INF111-01",
                "TP03",
                "01",
                "",
                "89,0",
                "100",
                "20",
                "65,1",
                "18,4",
                "68,0",
                "91",
                "Oui",
                "",
                "Non"
        )
        assertEquals(expectedEvaluation, wrapper.data!!.liste!![5])
    }

    @Test
    @Throws(IOException::class, InterruptedException::class)
    fun testGetListeElementsEvaluationError() {
        enqueueResponse("liste_elements_evaluation_error.json")

        val apiResponse = api.listeDesElementsEvaluation(
                "AM12345",
                "test",
                "INF111",
                "01",
                "H2015"
        )
        val wrapper: ApiSignetsModel<ApiListeDesElementsEvaluation> = getValue(apiResponse).body!!
        val listeDesElementsEvaluation = wrapper.data!!

        val errorStr = listeDesElementsEvaluation.erreur
        assertEquals("Cours ou bordereau de notes inexistant pour :LIUP27099105, LOG121E-01 à l'hiver 2016", errorStr)
        val type = listeDesElementsEvaluation.type
        assertEquals("SignetsPourEtudiants.SignetsMobile+ListeDesElementsEvaluation", type)
    }

    @Test
    @Throws(IOException::class, InterruptedException::class)
    fun testGetListeSessionsNoError() {
        enqueueResponse("liste_sessions_no_error.json")

        val apiResponse = api.listeSessions(SignetsUserCredentials(
                "AM41234",
                "test!"
        ))
        val wrapper: ApiSignetsModel<ApiListeDeSessions> = getValue(apiResponse).body!!

        val errorStr = wrapper.data!!.erreur
        assertTrue { errorStr == null || errorStr.isEmpty() }
        assertEquals(6, wrapper.data!!.liste.size)

        val expectedSession = ApiSession(
                "É2018",
                "Été 2018",
                "2018-04-30",
                "2018-08-11",
                "2018-08-01",
                "2018-02-26",
                "2018-03-13",
                "2018-04-30",
                "2018-05-11",
                "2018-05-11",
                "2018-05-22",
                "2018-07-04",
                "1964-01-01"
        )
        assertEquals(expectedSession, wrapper.data!!.liste[5])
        val type = wrapper.data!!.type
        assertEquals("SignetsPourEtudiants.SignetsMobile+ListeDeSessions", type)
    }

    @Test
    @Throws(IOException::class, InterruptedException::class)
    fun testGetListeSessionsError() {
        enqueueResponse("liste_sessions_error.json")

        val apiResponse = api.listeSessions(SignetsUserCredentials(
                "AM41234",
                "test!"
        ))
        val wrapper: ApiSignetsModel<ApiListeDeSessions> = getValue(apiResponse).body!!

        val errorStr = wrapper.data!!.erreur
        assertEquals("Code d'accès ou mot de passe invalide", errorStr)
        val type = wrapper.data!!.type
        assertEquals("SignetsPourEtudiants.SignetsMobile+ListeDeSessions", type)
    }

    @Test
    @Throws(IOException::class, InterruptedException::class)
    fun testGetListeSeancesNoError() {
        enqueueResponse("liste_seances_no_error.json")

        val apiResponse = api.listeDesSeances(
                "AM41234",
                "test!",
                "MAT472",
                "É2016",
                "2018-01-01",
                "2019-04-01"
        )

        val wrapper: ApiSignetsModel<ApiListeDesSeances> = getValue(apiResponse).body!!

        val errorStr = wrapper.data!!.erreur
        assertTrue { errorStr == null || errorStr.isEmpty() }
        assertEquals(26, wrapper.data!!.liste.size)

        val expectedSeance = ApiSeance(
                "/Date(1525093200000)/",
                "/Date(1525105800000)/",
                "MAT472-02",
                "Cours",
                "B-0904",
                "Activité de cours",
                "Algèbre linéaire et géométrie de l'espace"
        )

        assertEquals(expectedSeance, wrapper.data!!.liste[0])
        val type = wrapper.data!!.type
        assertEquals("SignetsPourEtudiants.SignetsMobile+listeSeances", type)
    }

    @Test
    @Throws(IOException::class, InterruptedException::class)
    fun testGetListeSeancesError() {
        enqueueResponse("liste_seances_error.json")

        val apiResponse = api.listeDesSeances(
                "AM41234",
                "test!",
                "MAT472",
                "É2016",
                "2018-01-01",
                "1 avril 2019"
        )
        val wrapper: ApiSignetsModel<ApiListeDesSeances> = getValue(apiResponse).body!!

        val errorStr = wrapper.data!!.erreur
        assertEquals("Date de fin. Date invalide, format attendu: aaaa-mm-jj. 1 avril 2019", errorStr)
        val type = wrapper.data!!.type
        assertEquals("SignetsPourEtudiants.SignetsMobile+listeSeances", type)
    }
}