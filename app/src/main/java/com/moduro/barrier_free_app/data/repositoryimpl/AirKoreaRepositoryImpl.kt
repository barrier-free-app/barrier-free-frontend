package com.moduro.barrier_free_app.data.repositoryimpl

import android.util.Log
import com.moduro.barrier_free_app.data.datasource.AirKoreaDataSource
import com.moduro.barrier_free_app.data.dto.response.AirItemDto
import com.moduro.barrier_free_app.domain.repository.AirKoreaRepository
import javax.inject.Inject

class AirKoreaRepositoryImpl @Inject constructor(
    private val dataSource: AirKoreaDataSource
) : AirKoreaRepository {
    override suspend fun getPm10Average(serviceKey: String): Result<Double?> {
        Log.d("AirKoreaRepo", "getPm10Average 함수 진입") // 맨 처음에 로그 추가
        return try {
            Log.d("AirKoreaRepo", "API 호출 시작")
            val items = dataSource.getAirKorea(serviceKey)
            Log.d("AirKoreaRepo", "API 응답 완료, 아이템 수: ${items.size}")

            val pm10Values = items.mapNotNull { it.pm10Value?.toIntOrNull() }
            val average = if (pm10Values.isNotEmpty()) pm10Values.average() else null
            Log.d("AirKoreaRepo", "pm10 평균값: $average")
            Result.success(average)
        } catch (e: Exception) {
            Log.e("AirKoreaRepo", "오류 발생: ${e.message}", e)
            Result.failure(e)
        }
    }
}