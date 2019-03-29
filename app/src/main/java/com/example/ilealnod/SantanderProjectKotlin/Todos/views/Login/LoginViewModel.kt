package com.example.ilealnod.SantanderProjectKotlin.Todos.views.Login

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.content.Context
import android.content.Intent
import com.example.ilealnod.SantanderProjectKotlin.Todos.Data.AccountInfoData
import com.example.ilealnod.SantanderProjectKotlin.Todos.Data.ClientData
import com.example.ilealnod.SantanderProjectKotlin.Todos.Data.UserInput
import com.example.ilealnod.SantanderProjectKotlin.Todos.Utils.LoginValidate
import com.example.ilealnod.SantanderProjectKotlin.Todos.datasource.GetCredentials
import com.example.ilealnod.SantanderProjectKotlin.Todos.datasource.LoginCallback
import com.example.ilealnod.SantanderProjectKotlin.Todos.datasource.WebServices
import com.example.ilealnod.SantanderProjectKotlin.Todos.views.statements.FormResponse
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel(application: Application, callback: LoginCallback) : AndroidViewModel(application) {

    private lateinit var clientDataLiveData: MutableLiveData<ClientData>
    private var clientData: ClientData
    private var activityCallback: LoginCallback
    private lateinit var accountInfoDataLiveData: MutableLiveData<AccountInfoData>
    private var accountInfoData: List<AccountInfoData>
    private var loginValidate: LoginValidate


    init {
        clientData = ClientData()
        accountInfoData = listOf(AccountInfoData())
        loginValidate = LoginValidate()
        activityCallback = callback
    }

    fun getObservableClientData(): LiveData<ClientData> {
        if (!::clientDataLiveData.isInitialized) {
            clientDataLiveData = MutableLiveData()
            activityCallback.onFailure()
        }
        return clientDataLiveData
    }

    fun getObservableStatementsData(): LiveData<AccountInfoData> {
        if (!::accountInfoDataLiveData.isInitialized) {
            accountInfoDataLiveData = MutableLiveData()
            activityCallback.onFailure()
        }
        return accountInfoDataLiveData
    }

    fun requestClientData(service: GetCredentials, userInput: UserInput, context: Context): ClientData {

        service.verifyLogin(userInput.login!!, userInput.password!!).enqueue(object : Callback<LoginResponse> {
            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                loginValidate.showToast(context, t.toString())
                activityCallback.onFailure()
            }

            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                clientData = response.body()?.clientData!!
                clientDataLiveData.value = clientData
                activityCallback.onSuccess()
            }
        })
        return clientData
    }

    fun requestAccountInfoData(context: Context, clientData: ClientData): List<AccountInfoData> {
        WebServices().getAccountInfo(clientData.userId!!).enqueue(object : Callback<FormResponse> {

            override fun onFailure(call: Call<FormResponse>, t: Throwable) {
                loginValidate.showToast(context, t.toString())
                activityCallback.onFailure()
            }

            override fun onResponse(call: Call<FormResponse>, response: Response<FormResponse>) {
                accountInfoData = response.body()?.list!!
                accountInfoDataLiveData.value = AccountInfoData()
                activityCallback.onSuccess()
            }
        })
        return accountInfoData
    }

    fun converterGforJ(gson: Gson, intent: Intent) {
        val stringDadoCliente = gson.toJson(clientData)
        val stringLista = gson.toJson(accountInfoData)
        intent.putExtra("DATACLIENT", stringDadoCliente)
        intent.putExtra("LISTACLIENT", stringLista)
    }
}