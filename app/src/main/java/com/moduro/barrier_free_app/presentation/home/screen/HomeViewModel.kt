package com.moduro.barrier_free_app.presentation.home.screen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moduro.barrier_free_app.data.dto.response.ResponseHomeHotPlaceDto
import com.moduro.barrier_free_app.domain.entity.HomePlaceEntity
import com.moduro.barrier_free_app.domain.repository.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository
) : ViewModel() {

    private val _hotPlaceList = MutableLiveData<List<HomePlaceEntity>>()
    val hotPlaceList: LiveData<List<HomePlaceEntity>> = _hotPlaceList

    // 로딩 상태 추가 (UX 향상에 도움)
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading


    fun getHotPlaces() {
        viewModelScope.launch {
            _isLoading.value = true
            val result = homeRepository.getHotPlaces()

            result.onSuccess { places ->
                _hotPlaceList.value = places

            }.onFailure { exception ->
                Log.e("HomeViewModel", "Fail", exception)
            }
            _isLoading.value = false
        }
    }


    val dummyWeatherPlaces = listOf(
        HomePlaceEntity(
            placeId = 1,
            placeType = "map",
            name = "종로 맛집",
            region = "종로구",
            description = "종로구의 유명한 맛집",
            facility = listOf(1, 2, 3),
            imageType = 1

        ),
        HomePlaceEntity(
            placeId = 1,
            placeType = "report",
            name = "종로 맛집",
            region = "종로구",
            description = "종로구의 유명한 맛집",
            facility = listOf(2, 4),
            imageType = 1

        ),
        HomePlaceEntity(
            placeId = 1,
            placeType = "report",
            name = "종로 맛집",
            region = "종로구",
            description = "종로구의 유명한 맛집",
            facility = listOf(1, 2, 3),
            imageType = 1

        )
    )


}
