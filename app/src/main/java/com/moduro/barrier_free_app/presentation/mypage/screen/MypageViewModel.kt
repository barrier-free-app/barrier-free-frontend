package com.moduro.barrier_free_app.presentation.mypage.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
class MypageViewModel @Inject constructor() : ViewModel() {

    private val _userInfo = MutableStateFlow<UserInfo?>(null)
    val userInfo: StateFlow<UserInfo?> = _userInfo

    init {
        loadUserInfo()
    }

    private fun loadUserInfo() {
        viewModelScope.launch {
            // 예시 더미 데이터
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

    fun onLogoutClick() {
        // 로그아웃 처리
    }

    fun onReviewClick() {
        // 내가 쓴 리뷰 화면 이동 처리
    }

    fun onFavoritePlaceClick() {
        // 좋아하는 장소 화면 이동 처리
    }

    fun onReportPlaceClick() {
        // 장소 제보 클릭 처리
    }

    //닉네임 변경 처리, 아직 API 연동은 하지 않았습니다 !!!
    private val _nicknameChangeStatus = MutableStateFlow(NicknameChangeStatus.NONE)
    val nicknameChangeStatus: StateFlow<NicknameChangeStatus> = _nicknameChangeStatus

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