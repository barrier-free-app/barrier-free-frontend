package com.moduro.barrier_free_app.presentation.mypage.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moduro.barrier_free_app.domain.entity.FavoritePlaceEntity
import com.moduro.barrier_free_app.domain.entity.ReviewPlaceEntity
import com.moduro.barrier_free_app.domain.repository.FavoriteToggleRepository
import com.moduro.barrier_free_app.domain.repository.MypageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class UserInfo(
    val userId: Int,
    val nickName: String,
    val userType: String,
    val userFacilities: List<Int>,
    val socialType: String,
    val email: String = "aaa@naver.com"
)

enum class NicknameChangeStatus {
    NONE, SUCCESS, DUPLICATE, LIMIT_EXCEEDED
}

@HiltViewModel
class MypageViewModel @Inject constructor(
    private val mypageRepository: MypageRepository,
    private val favoriteToggleRepository: FavoriteToggleRepository
) : ViewModel() {

    private val _userInfo = MutableStateFlow<UserInfo?>(null)
    val userInfo: StateFlow<UserInfo?> = _userInfo

    private val _favoritePlaces = MutableStateFlow<List<FavoritePlaceEntity>>(emptyList())
    val favoritePlaces: StateFlow<List<FavoritePlaceEntity>> = _favoritePlaces

    private val _reviewPlaces = MutableStateFlow<List<ReviewPlaceEntity>>(emptyList())
    val reviewPlaces: StateFlow<List<ReviewPlaceEntity>> = _reviewPlaces

    private val _nicknameChangeStatus = MutableStateFlow(NicknameChangeStatus.NONE)
    val nicknameChangeStatus: StateFlow<NicknameChangeStatus> = _nicknameChangeStatus

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage


    init {
        loadUserInfo()
        loadFavoritePlaces()
    }

    private fun loadUserInfo() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            val result = mypageRepository.getUserInfo()
            result.onSuccess { entity ->
                _userInfo.value = UserInfo(
                    userId = entity.userId.toInt(),
                    nickName = entity.nickname,
                    userType = entity.userType,
                    userFacilities = entity.userFacilities.map { it.facilityId },
                    socialType = entity.socialType,
                    email = entity.email
                )
            }.onFailure { e ->
                _errorMessage.value = "유저 정보를 불러오는데 실패했습니다: ${e.message}"
            }

            _isLoading.value = false
        }
    }


    private fun loadFavoritePlaces() {
        viewModelScope.launch {
            val result = mypageRepository.getFavoritePlaces()
            result.onSuccess { places ->
                _favoritePlaces.value = places
            }.onFailure {
                _favoritePlaces.value = emptyList()
            }
        }
    }

    fun loadReviewPlaces() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            val result = mypageRepository.getReviewPlaces()
            result.onSuccess { reviews ->
                _reviewPlaces.value = reviews
            }.onFailure { exception ->
                _reviewPlaces.value = emptyList()
                _errorMessage.value = "리뷰 목록을 불러오는데 실패했습니다: ${exception.message}"
            }
            _isLoading.value = false
        }
    }

    fun toggleFavorite(place: FavoritePlaceEntity) {
        viewModelScope.launch {
            _isLoading.value = true

            favoriteToggleRepository.toggleFavorite(place.id, place.type)
                .onSuccess { isNowFavorite ->
                    val updatedPlaces = _favoritePlaces.value.map { favoritePlace ->
                        if (favoritePlace.id == place.id) {
                            favoritePlace.copy(favorite = isNowFavorite)
                        } else {
                            favoritePlace
                        }
                    }

                    _favoritePlaces.value = if (isNowFavorite) {
                        updatedPlaces
                    } else {
                        updatedPlaces.filter { it.id != place.id }
                    }
                }
                .onFailure { exception ->
                    _errorMessage.value = "즐겨찾기 변경에 실패했습니다: ${exception.message}"
                }

            _isLoading.value = false
        }
    }

    fun removeFavoritePlace(place: FavoritePlaceEntity) {
        toggleFavorite(place)
    }

    fun refreshFavoritePlaces() {
        loadFavoritePlaces()
    }

    fun onLogoutClick() {
        // 로그아웃 처리
    }

    fun onReviewClick() {
        // 내가 쓴 리뷰 화면 이동 처리
    }

    fun changeNickname(newNickname: String) {
        viewModelScope.launch {
            if (newNickname == "버블티먹는코끼리") {
                _nicknameChangeStatus.value = NicknameChangeStatus.DUPLICATE
            } else if (newNickname == "한달초과시도") {
                _nicknameChangeStatus.value = NicknameChangeStatus.LIMIT_EXCEEDED
            } else {
                _userInfo.value = _userInfo.value?.copy(nickName = newNickname)
                _nicknameChangeStatus.value = NicknameChangeStatus.SUCCESS
            }
        }
    }
}
