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
import kotlinx.coroutines.flow.asStateFlow
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
    NONE, SUCCESS, DUPLICATE, LIMIT_EXCEEDED, ERROR, LOADING
}

enum class FacilityUpdateStatus {
    NONE, SUCCESS, ERROR, LOADING
}

enum class UserTypeUpdateStatus {
    NONE, SUCCESS, ERROR, LOADING
}

enum class PasswordUpdateStatus {
    NONE, SUCCESS, ERROR, LOADING
}

enum class AccountDeleteStatus {
    NONE, SUCCESS, ERROR, LOADING
}

@HiltViewModel
class MypageViewModel @Inject constructor(
    private val mypageRepository: MypageRepository,
    private val favoriteToggleRepository: FavoriteToggleRepository,
) : ViewModel() {

    private val _userInfo = MutableStateFlow<UserInfo?>(null)
    val userInfo: StateFlow<UserInfo?> = _userInfo.asStateFlow()

    private val _favoritePlaces = MutableStateFlow<List<FavoritePlaceEntity>>(emptyList())
    val favoritePlaces: StateFlow<List<FavoritePlaceEntity>> = _favoritePlaces.asStateFlow()

    private val _reviewPlaces = MutableStateFlow<List<ReviewPlaceEntity>>(emptyList())
    val reviewPlaces: StateFlow<List<ReviewPlaceEntity>> = _reviewPlaces.asStateFlow()

    private val _nicknameChangeStatus = MutableStateFlow(NicknameChangeStatus.NONE)
    val nicknameChangeStatus: StateFlow<NicknameChangeStatus> = _nicknameChangeStatus.asStateFlow()

    private val _userTypeUpdateStatus = MutableStateFlow(UserTypeUpdateStatus.NONE)
    val userTypeUpdateStatus: StateFlow<UserTypeUpdateStatus> = _userTypeUpdateStatus.asStateFlow()

    private val _facilityUpdateStatus = MutableStateFlow(FacilityUpdateStatus.NONE)
    val facilityUpdateStatus: StateFlow<FacilityUpdateStatus> = _facilityUpdateStatus.asStateFlow()

    private val _passwordUpdateStatus = MutableStateFlow(PasswordUpdateStatus.NONE)
    val passwordUpdateStatus: StateFlow<PasswordUpdateStatus> = _passwordUpdateStatus.asStateFlow()

    private val _accountDeleteStatus = MutableStateFlow(AccountDeleteStatus.NONE)
    val accountDeleteStatus: StateFlow<AccountDeleteStatus> = _accountDeleteStatus.asStateFlow()

    private val _logoutState = MutableStateFlow<Result<String>?>(null)
    val logoutState: StateFlow<Result<String>?> = _logoutState


    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

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

    fun changeNickname(newNickname: String) {
        if (newNickname.isBlank()) {
            _errorMessage.value = "닉네임을 입력해주세요."
            return
        }

        if (newNickname.length < 2 || newNickname.length > 10) {
            _errorMessage.value = "닉네임은 2~10자 사이로 입력해주세요."
            return
        }

        viewModelScope.launch {
            _nicknameChangeStatus.value = NicknameChangeStatus.LOADING
            _errorMessage.value = null

            val result = mypageRepository.changeNickname(newNickname)
            result.onSuccess {
                _userInfo.value = _userInfo.value?.copy(nickName = newNickname)
                _nicknameChangeStatus.value = NicknameChangeStatus.SUCCESS
            }.onFailure { throwable ->
                _nicknameChangeStatus.value = NicknameChangeStatus.ERROR
                _errorMessage.value = throwable.message ?: "닉네임 변경에 실패했습니다."
            }
        }
    }

    private fun validatePassword(password: String, verifyPassword: String): String? {
        return when {
            password.isBlank() || verifyPassword.isBlank() -> "비밀번호를 입력해주세요."
            password != verifyPassword -> "비밀번호가 일치하지 않습니다."
            password.length < 8 -> "비밀번호는 8자 이상이어야 합니다."
            else -> null
        }
    }

    fun updatePassword(password: String, verifyPassword: String) {
        val validationError = validatePassword(password, verifyPassword)
        if (validationError != null) {
            _errorMessage.value = validationError
            _passwordUpdateStatus.value = PasswordUpdateStatus.ERROR
            return
        }

        viewModelScope.launch {
            _passwordUpdateStatus.value = PasswordUpdateStatus.LOADING
            _isLoading.value = true
            _errorMessage.value = null

            val result = mypageRepository.updatePassword(password, verifyPassword)
            result.onSuccess {
                _passwordUpdateStatus.value = PasswordUpdateStatus.SUCCESS
            }.onFailure { throwable ->
                _passwordUpdateStatus.value = PasswordUpdateStatus.ERROR
                _errorMessage.value = throwable.message ?: "비밀번호 변경에 실패했습니다."
            }

            _isLoading.value = false
        }
    }

    fun updateUserType(userType: String) {
        viewModelScope.launch {
            _userTypeUpdateStatus.value = UserTypeUpdateStatus.LOADING
            _isLoading.value = true
            _errorMessage.value = null

            val result = mypageRepository.updateUserType(userType)
            result.onSuccess { message ->
                _userTypeUpdateStatus.value = UserTypeUpdateStatus.SUCCESS
                loadUserInfo()
            }.onFailure { throwable ->
                _userTypeUpdateStatus.value = UserTypeUpdateStatus.ERROR
                _errorMessage.value = throwable.message ?: "유저 타입 변경에 실패했습니다."
            }

            _isLoading.value = false
        }
    }

    fun updateFacilities(facilityIds: List<Int>) {
        viewModelScope.launch {
            _facilityUpdateStatus.value = FacilityUpdateStatus.LOADING
            _isLoading.value = true
            _errorMessage.value = null

            val result = mypageRepository.updateFacilities(facilityIds)
            result.onSuccess { message ->
                _facilityUpdateStatus.value = FacilityUpdateStatus.SUCCESS
                loadUserInfo()
            }.onFailure { throwable ->
                _facilityUpdateStatus.value = FacilityUpdateStatus.ERROR
                _errorMessage.value = throwable.message ?: "편의시설 정보 변경에 실패했습니다."
            }

            _isLoading.value = false
        }
    }

    fun resetNicknameChangeStatus() {
        _nicknameChangeStatus.value = NicknameChangeStatus.NONE
    }

    fun resetFacilityUpdateStatus() {
        _facilityUpdateStatus.value = FacilityUpdateStatus.NONE
    }

    fun resetUserTypeUpdateStatus() {
        _userTypeUpdateStatus.value = UserTypeUpdateStatus.NONE
    }

    fun resetPasswordUpdateStatus() {
        _passwordUpdateStatus.value = PasswordUpdateStatus.NONE
    }

    fun deleteAccount(reason: String) {
        if (reason.isBlank()) {
            _errorMessage.value = "탈퇴 사유를 입력해주세요."
            _accountDeleteStatus.value = AccountDeleteStatus.ERROR
            return
        }

        viewModelScope.launch {
            _accountDeleteStatus.value = AccountDeleteStatus.LOADING
            _isLoading.value = true
            _errorMessage.value = null

            val result = mypageRepository.deleteAccount(reason)
            result.onSuccess {
                _accountDeleteStatus.value = AccountDeleteStatus.SUCCESS
            }.onFailure { throwable ->
                _accountDeleteStatus.value = AccountDeleteStatus.ERROR
                _errorMessage.value = throwable.message ?: "회원 탈퇴에 실패했습니다."
            }

            _isLoading.value = false
        }
    }

    fun clearErrorMessage() {
        _errorMessage.value = null
    }

    fun onLogoutClick() {
        viewModelScope.launch {
            val result = mypageRepository.signOut()
            _logoutState.value = result
        }
    }

    fun refreshUserInfo() {
        loadUserInfo()
    }
}