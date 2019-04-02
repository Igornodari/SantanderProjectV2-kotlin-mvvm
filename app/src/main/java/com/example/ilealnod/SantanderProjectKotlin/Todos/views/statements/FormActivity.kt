package com.example.ilealnod.SantanderProjectKotlin.Todos.views.statements

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.StaggeredGridLayoutManager
import com.example.ilealnod.SantanderProjectKotlin.R
import com.example.ilealnod.SantanderProjectKotlin.Todos.Data.AccountInfoData
import com.example.ilealnod.SantanderProjectKotlin.Todos.Data.ClientData
import com.example.ilealnod.SantanderProjectKotlin.Todos.Utils.FormModel
import com.example.ilealnod.SantanderProjectKotlin.Todos.Utils.LoginModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.form_activity.*

class FormActivity : AppCompatActivity() {

    // implementação da tela de formulario
    private var clientData: ClientData = ClientData()
    private var loginModel: LoginModel = LoginModel()
    private var formModel: FormModel = FormModel()

    init {
        clientData = ClientData()
        loginModel = LoginModel()
        formModel = FormModel()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.form_activity)
        inicializarAcao()
        formModel.logout(btn_logout, getContext())

    }

    fun getContext(): Context {
        return applicationContext
    }

    private fun inicializarAcao() {
        getClientData()
        clientPopulate(clientData)
        getStatemant()
    }

    // Pega os dados da API da lista
    private fun getStatemant() {
        val json = intent.getStringExtra("LISTACLIENT")
        val collectionType = object : TypeToken<List<AccountInfoData>>() {
        }.type
        val list = Gson().fromJson(json, collectionType) as List<AccountInfoData>
        recycler.adapter = FormAdapter(list, this)

        val layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        recycler.layoutManager = layoutManager
    }

    // Pega os dados do Cliente
    fun getClientData() {
        val json = intent.getStringExtra("DATACLIENT")
        clientData = Gson().fromJson(json, ClientData::class.java)
    }

    // Mapeamento dos dados do cliente da api no layout
    fun clientPopulate(clientData: ClientData) {
        clientData.let {
            tv_nome_cliente.text = clientData.name
            tv_agencia.text = clientData.agency
            tv_saldoNumero.text = ("R$ " + clientData.balance.toString())
            tv_contaNumero.text = clientData.bankAccount
        }
    }
}