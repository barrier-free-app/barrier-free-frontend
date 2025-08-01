package com.moduro.barrier_free_app.presentation.auth.screen

import android.content.SharedPreferences
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moduro.barrier_free_app.data.dto.request.LoginRequestDto
import com.moduro.barrier_free_app.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: AuthRepository,
    private val preferences: SharedPreferences
): ViewModel() {
    var username by mutableStateOf("")
    var password by mutableStateOf("")

    var usernameError by mutableStateOf<String?>(null)
    var passwordError by mutableStateOf<String?>(null)

    val isLoginEnabled: Boolean
        get() = username.isNotBlank() && password.isNotBlank()

    private val _loginState = mutableStateOf<Boolean?>(null)
    val loginState: State<Boolean?> = _loginState

    private val _loginError = mutableStateOf<String?>(null)
    val loginError : State<String?> = _loginError

    // TODO 에러 메시지 세분화 .. 서버 오류는 로그로만 띄우기
    fun login(onSuccess: () -> Unit) {
        if (username.isBlank()) {
            usernameError = "아이디 혹은 이메일을 입력해주세요."
            return
        }
        if (password.isBlank()) {
            passwordError = "비밀번호를 입력해주세요."
            return
        }

        val loginRequestDto = LoginRequestDto(username, password)

        viewModelScope.launch {
            val result = repository.login(loginRequestDto)

            result.onSuccess { response ->
                val token = response.accessToken
                if (!token.isNullOrBlank()) {
                    saveToken(token)
                    onSuccess()
                } else {
                    passwordError = "서버 응답에 문제가 있어요. 다시 시도해주세요."
                }
            }.onFailure { exception ->
                Log.e("LoginViewModel", "로그인 실패", exception)
                passwordError = "서버 응답에 문제가 있어요. 다시 시도해주세요."
            }
        }
    }

    private fun saveToken(token : String){
        preferences.edit()
            .putString("USER_TOKEN", token)
            .commit()

        val savedToken = preferences.getString("USER_TOKEN", null)
        Log.d("LoginViewModel", "Saved Token: $savedToken")

    }

    fun resetLoginState() {
        _loginState.value = null
        _loginError.value = null
    }
}