package com.moduro.barrier_free_app.presentation.mypage.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moduro.barrier_free_app.domain.entity.FavoritePlaceEntity
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
    private val mypageRepository: MypageRepository
) : ViewModel() {

    private val _userInfo = MutableStateFlow<UserInfo?>(null)
    val userInfo: StateFlow<UserInfo?> = _userInfo

    private val _favoritePlaces = MutableStateFlow<List<FavoritePlaceEntity>>(emptyList())
    val favoritePlaces: StateFlow<List<FavoritePlaceEntity>> = _favoritePlaces

    private val _nicknameChangeStatus = MutableStateFlow(NicknameChangeStatus.NONE)
    val nicknameChangeStatus: StateFlow<NicknameChangeStatus> = _nicknameChangeStatus

    init {
        loadUserInfo()
        loadFavoritePlaces()
    }

    private fun loadUserInfo() {
        viewModelScope.launch {
            val dummyUser = UserInfo(
                userId = 123,
                nickName = "버블티먹는코끼리",
                userType = "DISABLED",
                userFacilities = listOf(1, 3, 4),
                socialType = "GENERAL",
                email = "aaa@naver.com"
            )
            _userInfo.value = dummyUser
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

    fun removeFavoritePlace(place: FavoritePlaceEntity) {
        viewModelScope.launch {
            // TODO: 서버에 좋아요 해제 API 호출 필요
            _favoritePlaces.value = _favoritePlaces.value.filter { it.id != place.id }
        }
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
