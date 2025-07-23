package com.moduro.barrier_free_app.presentation.home.screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moduro.barrier_free_app.BuildConfig
import com.moduro.barrier_free_app.domain.repository.LocationTempRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import javax.inject.Inject


@HiltViewModel
class LocationViewModel @Inject constructor(
    private val repository: LocationTempRepository
) : ViewModel() {

    private val _temperature = MutableStateFlow<String?>(null)
    val temperature: StateFlow<String?> = _temperature

    private val _rain = MutableStateFlow<String?>(null)
    val rain: StateFlow<String?> = _rain

    private val _sky = MutableStateFlow<String?>(null)
    val sky: StateFlow<String?> = _sky

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun fetchTemperature(latitude: Double, longitude: Double) {
        viewModelScope.launch {
            val (baseDate, baseTime) = getBaseDateTime()
            val (nx, ny) = convertToGridXY(latitude, longitude)

            val result = repository.getLatestTemperature(
                serviceKey = BuildConfig.PUBLIC_DATA_SERVICE_KEY,
                baseDate = baseDate,
                baseTime = baseTime,
                nx = nx,
                ny = ny
            )

            result.onSuccess { tempEntity ->
                _temperature.value = tempEntity?.temperature ?: "값 없음"
                _rain.value = tempEntity?.rain ?: "값 없음"
                _sky.value = tempEntity?.sky ?: "값 없음"

                _error.value = null

                Log.d(
                    "LocationViewModel",
                    "기온: ${tempEntity?.temperature}, 강수량: ${tempEntity?.rain}, 하늘상태: ${tempEntity?.sky}"
                )
            }.onFailure { e ->
                _error.value = e.message ?: "알 수 없는 오류"
            }
        }
    }


    private fun getBaseDateTime(): Pair<String, String> {
        val now = LocalDateTime.now(ZoneId.of("Asia/Seoul"))
        val formatterDate = DateTimeFormatter.ofPattern("yyyyMMdd")
        val formatterTime = DateTimeFormatter.ofPattern("HHmm")

        val minute = now.minute
        val baseMinute = if (minute < 30) 0 else 30
        val baseTime = now.withMinute(baseMinute).withSecond(0).withNano(0).minusMinutes(30)

        return Pair(baseTime.format(formatterDate), baseTime.format(formatterTime))
    }

    private fun convertToGridXY(lat: Double, lon: Double): GridXY {
        val RE = 6371.00877
        val GRID = 5.0
        val SLAT1 = 30.0
        val SLAT2 = 60.0
        val OLON = 126.0
        val OLAT = 38.0
        val XO = 43
        val YO = 136

        val DEGRAD = Math.PI / 180.0
        val re = RE / GRID
        val slat1 = SLAT1 * DEGRAD
        val slat2 = SLAT2 * DEGRAD
        val olon = OLON * DEGRAD
        val olat = OLAT * DEGRAD

        val sn = Math.tan(Math.PI * 0.25 + slat2 * 0.5) / Math.tan(Math.PI * 0.25 + slat1 * 0.5)
        val snLog = Math.log(Math.cos(slat1) / Math.cos(slat2)) / Math.log(sn)
        val sf = Math.tan(Math.PI * 0.25 + slat1 * 0.5)
        val sfPow = Math.pow(sf, snLog) * Math.cos(slat1) / snLog
        val ro = re * sfPow / Math.pow(Math.tan(Math.PI * 0.25 + olat * 0.5), snLog)

        val ra = re * sfPow / Math.pow(Math.tan(Math.PI * 0.25 + lat * DEGRAD * 0.5), snLog)
        var theta = (lon - OLON) * DEGRAD
        if (theta > Math.PI) theta -= 2.0 * Math.PI
        if (theta < -Math.PI) theta += 2.0 * Math.PI

        val x = ra * Math.sin(theta * snLog) + XO + 0.5
        val y = ro - ra * Math.cos(theta * snLog) + YO + 0.5

        return GridXY(x.toInt(), y.toInt())
    }
}
