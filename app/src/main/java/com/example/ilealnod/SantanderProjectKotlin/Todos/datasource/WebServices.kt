package com.example.ilealnod.SantanderProjectKotlin.Todos.datasource

import com.example.ilealnod.SantanderProjectKotlin.Todos.views.statements.FormResponse
import retrofit2.Call


class WebServices {

    // Função auxiliar para puxar os dados da api de statements pelo ID do usuario

    var service: GetCredentials = RetrofitCall().getCall().create(GetCredentials::class.java)

    fun getAccountInfo(userId: Int): Call<FormResponse> {
        val url = "statements/$userId"
        return service.getAccountInfo(url)
    }
}


