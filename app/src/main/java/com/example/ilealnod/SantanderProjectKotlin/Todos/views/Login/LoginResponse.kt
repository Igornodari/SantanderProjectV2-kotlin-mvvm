package com.example.ilealnod.SantanderProjectKotlin.Todos.views.Login

import com.example.ilealnod.SantanderProjectKotlin.Todos.Data.ClientData
import com.google.gson.annotations.SerializedName

// Classe que pega os Dados do cliente / resposta da APi

class LoginResponse {

    @SerializedName("userAccount")
    var clientData: ClientData? = null
}
