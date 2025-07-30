package com.moduro.barrier_free_app.presentation.auth.screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moduro.barrier_free_app.domain.repository.AuthRepository
import com.moduro.barrier_free_app.presentation.mypage.screen.UserInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authRepository: AuthRepository
): ViewModel() {
    private val _userInfo = MutableStateFlow<UserInfo?>(null)
    val userInfo: StateFlow<UserInfo?> = _userInfo

    var email by mutableStateOf("")
    var verificationCode by mutableStateOf("")
    var id by mutableStateOf("")
    var password by mutableStateOf("")

    var currentStep by mutableStateOf(1)

    val isSignUpEnabled: Boolean
        get() = email.isNotBlank()

    fun setNickname(nicknameInput: String) {
        // TODO: 닉네임 중복 확인 API 호출
    }

    fun sendVerificationCode(
        email: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        viewModelScope.launch {
            val result = authRepository.send(email)
            if (result.isSuccess) onSuccess()
            else onFailure(result.exceptionOrNull()?.message ?: "전송 실패, 다시 시도해 주세요")
        }
    }

    fun verifyVerificationCode(
        email: String,
        verificationCode: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        viewModelScope.launch {
            val result = authRepository.verify(email, verificationCode)
            if (result.isSuccess) onSuccess()
            else onFailure(result.exceptionOrNull()?.message ?: "인증 실패, 다시 시도해 주세요")
        }
    }
}