package com.example.ilealnod.SantanderProjectKotlin.Todos.Utils

import android.content.Context


// Classe onde se localiza toda a logica da Login ACTIVITY

class LoginModel {

    private var loginValidate: LoginValidate? = null

    init {
        loginValidate = LoginValidate()
    }

    // Função para verificar os dados imputados no Login e Senha
    fun checkLogin(user: String, pass: String, context: Context): Boolean {

        return if (loginValidate!!.checkUserField(user) || loginValidate!!.checkUserField(pass)) {
            loginValidate!!.showToast(context, "Erro:User or Pass Empty Field")
            true

        } else if (!loginValidate!!.isValid(user)) {
            loginValidate!!.showToast(context, "Erro: User Formate Error ")
            true

        } else if (!loginValidate!!.checkPassword(pass)) {
            loginValidate!!.showToast(context, "Erro: Password Formate Error")
            true

        } else {
            loginValidate!!.showToast(context, "Login Succes")
            false
        }

    }

}

