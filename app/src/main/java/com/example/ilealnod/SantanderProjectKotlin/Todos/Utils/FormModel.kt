package com.example.ilealnod.SantanderProjectKotlin.Todos.Utils

import android.content.Context
import android.widget.ImageView
import kotlin.system.exitProcess

class FormModel {

    private var loginValidate: LoginValidate = LoginValidate()

    fun logout(btn_logout: ImageView, context: Context) {
        btn_logout.setOnClickListener {
            loginValidate.showToast(context, "thanks :)")
            exitProcess(0)
        }

    }

}