package com.example.ilealnod.SantanderProjectKotlin.Todos.views.statements

import com.example.ilealnod.SantanderProjectKotlin.Todos.Data.AccountInfoData
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class FormResponse {

    // Resposta da chamada
    @SerializedName("statementList")
    @Expose
    var list: List<AccountInfoData>? = null
}