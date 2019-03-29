package com.example.ilealnod.SantanderProjectKotlin.Todos.Data

import com.google.gson.annotations.SerializedName

class ClientData {

    constructor(userId: Int?, name: String?, bankAccount: String?, agency: String?, balance: Double?) {
        this.userId = userId
        this.name = name
        this.bankAccount = bankAccount
        this.agency = agency
        this.balance = balance
    }

    constructor()

    // Dependencia dos Dados do cliente

    @SerializedName("userId")
    var userId: Int? = 0
    @SerializedName("name")
    var name: String? = null
    @SerializedName("bankAccount")
    var bankAccount: String? = null
    @SerializedName("agency")
    var agency: String? = null
    @SerializedName("balance")
    var balance: Double? = 0.0

}
