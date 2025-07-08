package com.moduro.barrier_free_app.presentation.auth.screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {

    var email by mutableStateOf("")
    var password by mutableStateOf("")

    var emailError by mutableStateOf<String?>(null)
    var passwordError by mutableStateOf<String?>(null)

    val  isLoginEnabled: Boolean
        get() = email.isNotBlank() && password.isNotBlank()

    fun login(onSuccess: () -> Unit) {
        if (email != "test@gmail.com") {
            emailError = "존재하지 않는 회원입니다."
            passwordError = null
            return
        }

        if (password != "1234") {
            emailError = null
            passwordError = "비밀번호 오류입니다.\n5번 제한 시 비밀번호 찾기가 필요합니다." //TODO 비밀번호 몇번 틀렸는지 띄우기
            return
        }

        emailError = null
        passwordError = null
        onSuccess()
    }
}