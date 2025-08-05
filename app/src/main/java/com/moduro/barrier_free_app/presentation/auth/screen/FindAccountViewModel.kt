package com.moduro.barrier_free_app.presentation.auth.screen

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moduro.barrier_free_app.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class FindMode {
    ID, PASSWORD
}

@HiltViewModel
class FindAccountViewModel @Inject constructor(
    private val repository: AuthRepository
): ViewModel() {

    var selectedMode by mutableStateOf(FindMode.ID)
    var email by mutableStateOf("")

    val isEmailEntered: Boolean
        get() = email.isNotBlank()

    fun switchToId() {
        selectedMode = FindMode.ID
    }

    fun switchToPW() {
        selectedMode = FindMode.PASSWORD
    }

    fun onEmailChange(new: String) {
        email = new
    }

    fun onSubmit(onSuccess: () -> Unit) {
        viewModelScope.launch {
            val type = when (selectedMode) {
                FindMode.ID -> "username"
                FindMode.PASSWORD -> "password"
            }

            val result = repository.find(type, email)

            result.onSuccess {
                onSuccess()
            }.onFailure {
                Log.e("FindAccount", "계정 찾기 실패", it)
            }
        }
    }
}