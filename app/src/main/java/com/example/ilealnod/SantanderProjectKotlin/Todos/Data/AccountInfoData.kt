package com.example.ilealnod.SantanderProjectKotlin.Todos.Data

import com.google.gson.annotations.SerializedName

class AccountInfoData {

    //Dependencia de Informações da conta

    @SerializedName("title")
    var title: String? = null
    @SerializedName("desc")
    var desc: String? = null
    @SerializedName("date")
    var date: String? = null
    @SerializedName("value")
    var value: Double? = null

}
