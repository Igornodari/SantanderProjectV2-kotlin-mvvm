package com.example.ilealnod.SantanderProjectKotlin.Todos.views.Login

import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.example.ilealnod.SantanderProjectKotlin.R
import com.example.ilealnod.SantanderProjectKotlin.Todos.Data.AccountInfoData
import com.example.ilealnod.SantanderProjectKotlin.Todos.Data.ClientData
import com.example.ilealnod.SantanderProjectKotlin.Todos.Data.UserInput
import com.example.ilealnod.SantanderProjectKotlin.Todos.Utils.LoginModel
import com.example.ilealnod.SantanderProjectKotlin.Todos.datasource.GetCredentials
import com.example.ilealnod.SantanderProjectKotlin.Todos.datasource.LoginCallback
import com.example.ilealnod.SantanderProjectKotlin.Todos.datasource.RetrofitCall
import com.example.ilealnod.SantanderProjectKotlin.Todos.views.statements.FormActivity
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_login.*

open class LoginActivity : AppCompatActivity(), LoginCallback {

    override fun onFailure() {
        progress_circular.visibility = View.GONE
    }

    override fun onSuccess() {
        progress_circular.visibility = View.VISIBLE
    }

    // Implementação da Tela de Login
    private var loginViewModel: LoginViewModel? = null
    private lateinit var retrofit: RetrofitCall
    private lateinit var userInput: UserInput
    private lateinit var clientData: ClientData
    private lateinit var accountInfoData: List<AccountInfoData>
    private lateinit var loginModel: LoginModel
    private lateinit var service: GetCredentials
    private lateinit var gson: Gson
    private lateinit var user: String
    private lateinit var pass: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        iniciarVariaveis()
        getContext()
        onClick()
    }

    private fun iniciarVariaveis() {
        gson = Gson()
        retrofit = RetrofitCall()
        clientData = ClientData()
        accountInfoData = listOf(AccountInfoData())
        loginViewModel = LoginViewModel(application, this@LoginActivity)
        loginModel = LoginModel()
        service = retrofit.getCall().create(GetCredentials::class.java)
    }

    // Função que pega os dados digitados pelo usuario e chama a função de validar se o usuario
    // e senha estão corretos !
    private fun onClick() {
        btn_login.setOnClickListener {

            onSuccess()
            user = edt_login_user.text.toString()
            pass = edt_login_password.text.toString()
            userInput = UserInput(user, pass)

            if (!loginModel.checkLogin(user, pass, getContext())) {
                Requests()
            } else {
                onFailure()
            }
        }
    }

    // Função que pega o contexto
    fun getContext(): Context {
        return applicationContext
    }

    // Função que faz o chama os dados para ser populado no objeto
    private fun Requests() {

        loginViewModel!!.requestClientData(service, userInput, getContext())

        loginViewModel!!.getObservableClientData().observe(this, Observer {


            loginViewModel!!.requestAccountInfoData(this.getContext(), clientData)

            loginViewModel!!.getObservableStatementsData().observe(this, Observer {

                nextPage(getContext())
            })
        })
    }

    //Função que vai para pagina de formulario passando os objetos convertidos em Json
    fun nextPage(context: Context) {
        progress_circular.visibility = View.GONE
        val intent = Intent(context, FormActivity::class.java)
        loginViewModel!!.converterGforJ(gson, intent)
        startActivity(intent)
    }
}