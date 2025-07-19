package com.moduro.barrier_free_app.presentation.auth.screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.moduro.barrier_free_app.presentation.mypage.screen.UserInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SignUpViewModel : ViewModel() {

    private val _userInfo = MutableStateFlow<UserInfo?>(null)
    val userInfo: StateFlow<UserInfo?> = _userInfo

    var email by mutableStateOf("")
    var verificationCode by mutableStateOf("")
    var id by mutableStateOf("")
    var password by mutableStateOf("")

    var currentStep by mutableStateOf(1)

    val isSignUpEnabled: Boolean
        get() = email.isNotBlank()

    val isEmailValid: Boolean
        get() = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()

    fun signUp(onSuccess: () -> Unit) {
        onSuccess()
    }

    fun setNickname(nicknameInput: String) {
        // TODO: 닉네임 중복 확인 API 호출
    }
}