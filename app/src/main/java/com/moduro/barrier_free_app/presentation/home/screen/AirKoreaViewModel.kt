package com.moduro.barrier_free_app.presentation.home.screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moduro.barrier_free_app.BuildConfig
import com.moduro.barrier_free_app.domain.repository.AirKoreaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class AirKoreaViewModel @Inject constructor(
    private val repository: AirKoreaRepository
) : ViewModel() {

    private val _pm10Average = MutableStateFlow<Double?>(null)
    val pm10Average: StateFlow<Double?> = _pm10Average

    private val _pm10Grade = MutableStateFlow<Int>(1)
    val pm10Grade: StateFlow<Int> = _pm10Grade

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun fetchPm10Average() {
        viewModelScope.launch {
            val result = repository.getPm10Average(BuildConfig.AIR_KOREA_SERVICE_KEY)
            result.onSuccess { avg ->
                _pm10Average.value = avg
                _pm10Grade.value = getPm10Grade(avg)
                _error.value = null
                Log.d("AirKoreaView", "pm10 평균값: $avg")
            }.onFailure { e ->
                _error.value = e.message ?: "알 수 없는 오류"
            }
        }
    }

    private fun getPm10Grade(pm10: Double?): Int{
        return when {
            pm10 == null -> 3
            pm10 <= 30 -> 2
            pm10 <= 80 -> 3
            else -> 1
        }
    }
}
