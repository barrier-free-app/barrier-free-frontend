package com.moduro.barrier_free_app.presentation.auth.screen

import android.content.SharedPreferences
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moduro.barrier_free_app.data.dto.request.TokenRequestDto
import com.moduro.barrier_free_app.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface OAuthUiState {
    data object Idle: OAuthUiState
    data object Exchanging: OAuthUiState
    data object Success: OAuthUiState
    data class Error(val message: String): OAuthUiState
}

@HiltViewModel
class OAuthViewModel @Inject constructor(
    private val repository: AuthRepository,
    private val preferences: SharedPreferences
): ViewModel() {

    private val _uiState = MutableStateFlow<OAuthUiState>(OAuthUiState.Idle)
    val uiState: StateFlow<OAuthUiState> = _uiState

    var lastStartedProvider: String? = null
        private set
    fun setProvider(type: String) {
        lastStartedProvider = type
        preferences.edit().putString("LAST_PROVIDER", type).apply()
    }

    fun handleDeepLink(uri: Uri) {
        val type = uri.getQueryParameter("type") ?: lastStartedProvider ?: preferences.getString("LAST_PROVIDER", null)
        val code = uri.getQueryParameter("authCode") ?: uri.getQueryParameter("code")
        val state = uri.getQueryParameter("state")

        if (type.isNullOrBlank()) return fail("type 없음")
        if (code.isNullOrBlank()) return fail("authCode/code 없음")
        if (type != "kakao" && state.isNullOrBlank()) return fail("state 없음")

        exchange(type, code, state)
    }

    private fun exchange(type: String, code: String, state: String?) = viewModelScope.launch {
        try {
            _uiState.value = OAuthUiState.Exchanging
            val req = TokenRequestDto(type = type, authCode = code, state = state)
            val result = repository.exchangeToken(req)
            result.onSuccess { res ->
                val access = res.accessToken
                if (!access.isNullOrBlank()) {
                    saveToken(access)
                    preferences.edit().remove("LAST_PROVIDER").apply()
                    _uiState.value = OAuthUiState.Success
                } else {
                    fail("서버 응답에 accessToken 없음")
                }
            }.onFailure { e ->
                fail(e.message ?: "토큰 교환 실패")
            }
        } catch (e: Exception) {
            fail("예상치 못한 오류")
        }
    }

    private fun saveToken(token: String) {
        preferences.edit().putString("USER_TOKEN", token).apply()
    }

    private fun fail(msg: String) { _uiState.value = OAuthUiState.Error(msg) }
}
