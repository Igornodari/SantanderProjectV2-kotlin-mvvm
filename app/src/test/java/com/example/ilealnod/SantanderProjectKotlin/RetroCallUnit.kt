package com.example.ilealnod.SantanderProjectKotlin

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.ilealnod.SantanderProjectKotlin.Todos.Data.AccountInfoData
import com.example.ilealnod.SantanderProjectKotlin.Todos.Data.ClientData
import com.example.ilealnod.SantanderProjectKotlin.Todos.Data.UserInput
import com.example.ilealnod.SantanderProjectKotlin.Todos.Utils.LoginModel
import com.example.ilealnod.SantanderProjectKotlin.Todos.datasource.GetCredentials
import com.example.ilealnod.SantanderProjectKotlin.Todos.datasource.WebServices
import com.example.ilealnod.SantanderProjectKotlin.Todos.views.Login.LoginActivity
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import okhttp3.mockwebserver.MockWebServer
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException


class RetroCallUnit {

    // Testes da Chamada da api / Populando os objetos
    val pass: String = "T@1"
    val user: String = "igor.lealnodari@hotmail.com"

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var userImput: UserInput
    lateinit var mainViewModel: LoginModel
    lateinit var service: WebServices
    lateinit var loginResponse: List<AccountInfoData>
    lateinit var loginActivity: LoginActivity
    lateinit var clientData: ClientData
    private var BASE_URL: String = "https://bank-app-test.herokuapp.com/api/"

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        this.loginActivity = LoginActivity()
        this.mainViewModel = LoginModel()
        this.userImput = UserInput(user, pass)
        this.service = WebServices()
        this.loginResponse = listOf(AccountInfoData())
        this.clientData = ClientData()
    }

    @Test
    @Throws(IOException::class)
    // testa a requisição dos dados da account info
    fun testAccountInfoDataRequest() {
        val mockWebServer = MockWebServer()
        mockWebServer.start()

        val retrofit = Retrofit.Builder()
            .baseUrl(mockWebServer.url(BASE_URL))
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(GetCredentials::class.java)
        val call = service.getAccountInfo("statements/1)")
        val response = call.execute()

        assertEquals(200, response.code())
        assertNotNull(response.body()!!.list)
        mockWebServer.shutdown()
    }

    @Test
    fun testClientDataRequest() {
        val mockWebServer = MockWebServer()
        mockWebServer.start()

        val retrofit = Retrofit.Builder()
            .baseUrl(mockWebServer.url(BASE_URL))
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(GetCredentials::class.java)
        val call = service.verifyLogin("user", "pass")
        val response = call.execute()

        assertEquals(200, response.code())
        assertNotNull(response.body()?.clientData)
        mockWebServer.shutdown()

    }
}
