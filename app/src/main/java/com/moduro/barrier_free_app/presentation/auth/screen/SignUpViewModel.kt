package com.moduro.barrier_free_app.presentation.auth.screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moduro.barrier_free_app.domain.repository.AuthRepository
import com.moduro.barrier_free_app.presentation.mypage.screen.NicknameChangeStatus
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

    var inputEmail by mutableStateOf("")
    var verificationCode by mutableStateOf("")
    var id by mutableStateOf("")
    var password by mutableStateOf("")

    var currentStep by mutableStateOf(1)

    val isSignUpEnabled: Boolean
        get() = inputEmail.isNotBlank()

    fun setEmail(email: String) {
        this.inputEmail = email
    }

    fun setNicknameChangeStatus(status: NicknameChangeStatus) {
        _nicknameChangeStatus.value = status
    }

    fun setIdStatus(status: NicknameChangeStatus) {
        _idStatus.value = status
    }

    private val _nicknameChangeStatus = MutableStateFlow(NicknameChangeStatus.NONE)
    val nicknameChangeStatus: StateFlow<NicknameChangeStatus> = _nicknameChangeStatus

    private val _idStatus = MutableStateFlow<NicknameChangeStatus>(NicknameChangeStatus.NONE)
    val idStatus: StateFlow<NicknameChangeStatus> = _idStatus

    fun checkDuplicate(
        type: String,
        input: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        viewModelScope.launch {
            val result = authRepository.duplicate(type, input)
            if (result.isSuccess) {
                when (type) {
                    "nickname" -> _nicknameChangeStatus.value = NicknameChangeStatus.SUCCESS
                    "username" -> _idStatus.value = NicknameChangeStatus.SUCCESS
                }
                onSuccess()
            } else {
                when (type) {
                    "nickname" -> _nicknameChangeStatus.value = NicknameChangeStatus.DUPLICATE
                    "username" -> _idStatus.value = NicknameChangeStatus.DUPLICATE
                }
                onFailure(result.exceptionOrNull()?.message?: "중복 확인 실패")
            }
        }
    }

    fun sendVerificationCode(
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        viewModelScope.launch {
            val result = authRepository.send(inputEmail)
            if (result.isSuccess) onSuccess()
            else onFailure(result.exceptionOrNull()?.message ?: "전송 실패")
        }
    }

    fun verifyVerificationCode(
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        viewModelScope.launch {
            val result = authRepository.verify(inputEmail, verificationCode)
            if (result.isSuccess) onSuccess()
            else onFailure(result.exceptionOrNull()?.message ?: "인증 실패")
        }
    }

    fun submitSignUpSetting(
        email: String,
        nickname: String,
        username: String,
        password: String,
        verifyPassword: String,
        userType: String,
        userFacilityIds: List<Int>,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        viewModelScope.launch {
            val result = authRepository.signup(
                email = email,
                nickname = nickname,
                username = username,
                password = password,
                verifyPassword = verifyPassword,
                userType = userType,
                userFacilityIds = userFacilityIds
            )
            if (result.isSuccess) {
                onSuccess()
            } else {
                onFailure(result.exceptionOrNull()?.message ?: "회원가입 실패")
            }
        }
    }
}