package com.moduro.barrier_free_app.presentation.auth.screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

enum class FindMode {
    ID, PASSWORD
}

class FindAccountViewModel: ViewModel() {

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
        //TODO 이메일 전송
        onSuccess()
    }
}