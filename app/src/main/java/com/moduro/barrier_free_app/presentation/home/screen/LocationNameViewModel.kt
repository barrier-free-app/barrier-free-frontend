package com.moduro.barrier_free_app.presentation.home.screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moduro.barrier_free_app.domain.repository.LocationNameRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationNameViewModel @Inject constructor(
    private val repository: LocationNameRepository
) : ViewModel() {

    private val _locationName = MutableStateFlow<String?>(null)
    val locationName: StateFlow<String?> = _locationName

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun getLocationName(latitude: Double, longitude: Double) {
        Log.d("HomeScreen", "getLocationName 진입")
        viewModelScope.launch {
            val result = repository.getAddress(
                longitude = longitude,
                latitude = latitude
            )

            result.onSuccess { responseDto ->
                val firstDoc = responseDto.documents.firstOrNull()
                val gu = firstDoc?.region2depthName
                val dong = firstDoc?.region3depthName

                _locationName.value = if (!gu.isNullOrBlank() && !dong.isNullOrBlank()) {
                    "$gu $dong"
                } else {
                    "주소 정보 없음"
                }

                _error.value = null
                Log.d("LocationViewModel", "주소: ${_locationName.value}")


            }.onFailure { e ->
                _error.value = e.message ?: "알 수 없는 오류"
            }
        }
    }


}