package com.example.ilealnod.SantanderProjectKotlin.Todos.datasource

import com.example.ilealnod.SantanderProjectKotlin.Todos.views.Login.LoginResponse
import com.example.ilealnod.SantanderProjectKotlin.Todos.views.statements.FormResponse
import retrofit2.Call
import retrofit2.http.*

interface GetCredentials {

    // Implementação dos metodos GET /POST e de validação da chamada

    @POST("login")
    @FormUrlEncoded
    fun verifyLogin(
            @Field("user") login: String,
            @Field("password") password: String
    ): Call<LoginResponse>

    @GET
    fun getAccountInfo(@Url url: String): Call<FormResponse>
}