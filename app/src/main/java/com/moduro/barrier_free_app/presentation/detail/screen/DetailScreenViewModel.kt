package com.moduro.barrier_free_app.presentation.detail.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moduro.barrier_free_app.core_ui.component.RecommendType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor() : ViewModel() {

    private val _placeDetail = MutableStateFlow<PlaceDetail?>(null)
    val placeDetail: StateFlow<PlaceDetail?> = _placeDetail

    private val _visibleReviewsCount = MutableStateFlow(2)
    val visibleReviewsCount: StateFlow<Int> = _visibleReviewsCount

    private val _recommendType = MutableStateFlow<RecommendType?>(null)
    val recommendType: StateFlow<RecommendType?> = _recommendType

    fun onThumbUpClicked() {
        _recommendType.value = RecommendType.THUMB_UP
    }

    fun onThumbDownClicked() {
        _recommendType.value = RecommendType.THUMB_DOWN
    }

    fun resetRecommend() {
        _recommendType.value = null
    }

    fun toggleFavorite() {
        _placeDetail.value = _placeDetail.value?.copy(
            isFavorite = !_placeDetail.value!!.isFavorite
        )
    }

    fun loadMoreReviews() {
        _visibleReviewsCount.value += 2
    }

    init {
        loadPlaceDetail()
    }

    private fun loadPlaceDetail() {
        viewModelScope.launch {
            val dummy = PlaceDetail(
                id = 1,
                name = "국립현대미술관 서울 MMCA",
                imageUrl = "대체이미지url",
                address = "서울 종로구 삼청로 30",
                openingHours = "10:00 - 18:00",
                facilities = listOf(1, 3, 5),
                isFavorite = true,
                averageRating = 4.0,
                reviewCount = 46,
                reviews = listOf(
                    Review("눈송스", 5, "배리어프리 시설이 잘 되어 편리하고 좋아요", "2025-05-09"),
                    Review("눈송송", 3, "휠체어를 타면 좀 돌아가야 하지만 나쁘지 않았어요", "2025-05-08"),
                    Review("눈송송2", 6, "휠체어를 타면 좀 돌아가야 하지만 나쁘지 않았어요", "2025-05-08")
                )
            )
            _placeDetail.value = dummy
        }
    }
}


data class PlaceDetail(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val address: String,
    val openingHours: String,
    val facilities: List<Int>,
    val isFavorite: Boolean,
    val averageRating: Double,
    val reviewCount: Int,
    val reviews: List<Review>
)

data class Review(
    val userNickname: String,
    val rating: Int,
    val comment: String,
    val createdAt: String
)

