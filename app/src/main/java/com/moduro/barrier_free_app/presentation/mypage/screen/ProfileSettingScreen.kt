package com.moduro.barrier_free_app.presentation.mypage.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.moduro.barrier_free_app.R
import com.moduro.barrier_free_app.core_ui.component.CommonTopBar
import com.moduro.barrier_free_app.core_ui.component.ProfileFacilityChip
import com.moduro.barrier_free_app.core_ui.component.ProfileNicknameField
import com.moduro.barrier_free_app.core_ui.component.ProfilePasswordField
import com.moduro.barrier_free_app.core_ui.theme.Background1
import com.moduro.barrier_free_app.core_ui.theme.Background2
import com.moduro.barrier_free_app.core_ui.theme.Button1
import com.moduro.barrier_free_app.core_ui.theme.LocalbarrierFreeTypographyProvider
import com.moduro.barrier_free_app.core_ui.theme.Text4
import com.moduro.barrier_free_app.core_ui.theme.Text5

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ProfileSettingScreen(
    isEmailUser: Boolean,
    onBackClick: () -> Unit,
    onNavigateToMypage: () -> Unit,
    viewModel: MypageViewModel = hiltViewModel()
) {

    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.setSystemBarsColor(color = Background2)
    }

    val typography = LocalbarrierFreeTypographyProvider.current

    val userInfo by viewModel.userInfo.collectAsState()
    val nicknameChangeStatus by viewModel.nicknameChangeStatus.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    var nicknameInput by remember { mutableStateOf("") }
    var isNicknameChanged by remember { mutableStateOf(false) }

    LaunchedEffect(nicknameChangeStatus) {
        when (nicknameChangeStatus) {
            NicknameChangeStatus.SUCCESS -> {
                isNicknameChanged = true
                nicknameInput = ""
            }
            else -> {}
        }
    }

    // 사용자 타입 라디오 버튼 (단일 선택)
    var selectedUserType by remember { mutableStateOf("전체") }

    // userInfo가 로드되면 초기값 설정
    LaunchedEffect(userInfo) {
        userInfo?.let { info ->
            selectedUserType = when (info.userType.uppercase()) {
                "ALL" -> "전체"
                "DISABLED" -> "장애인"
                "PREGNANT" -> "임산부 및 영유아 동반"
                else -> "전체"
            }
        }
    }

    val selectedFacilities = remember {
        mutableStateListOf<String>().apply {
            userInfo?.userFacilities?.forEach { id -> add(id.toString()) }
        }
    }

    // 유저 타입이 변경되었는지 확인
    fun isUserTypeChanged(): Boolean {
        val currentUserType = userInfo?.userType?.uppercase() ?: "ALL"
        val newUserType = when (selectedUserType) {
            "전체" -> "ALL"
            "장애인" -> "DISABLED"
            "임산부 및 영유아 동반" -> "PREGNANT"
            else -> "ALL"
        }
        return currentUserType != newUserType
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background2)
    ) {
        CommonTopBar(title = "프로필 설정", onBackClick = onBackClick)

        Column(modifier = Modifier.padding(horizontal = 20.dp)) {

            Text(
                text = "닉네임 변경",
                style = typography.H5_SB_5,
                color = Text4,
                modifier = Modifier.padding(top = 20.dp, bottom = 8.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                ProfileNicknameField(
                    hint = "현재 닉네임은 ${userInfo?.nickName} 입니다.",
                    text = nicknameInput,
                    onValueChange = {
                        nicknameInput = it
                        if (nicknameChangeStatus != NicknameChangeStatus.NONE) {
                            viewModel.resetNicknameChangeStatus()
                        }
                        if (errorMessage != null) {
                            viewModel.clearErrorMessage()
                        }
                    },
                    onCheckDuplicateClick = {
                        if (nicknameInput.isNotBlank()) {
                            viewModel.changeNickname(nicknameInput)
                        }
                    },
                    modifier = Modifier.weight(1f)
                )

                // 로딩 상태 표시
                if (nicknameChangeStatus == NicknameChangeStatus.LOADING) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .width(20.dp)
                            .height(20.dp),
                        strokeWidth = 2.dp,
                        color = Text5
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // 일반 에러 메시지 (닉네임 유효성 검사 등)
            errorMessage?.let { message ->
                if (nicknameChangeStatus == NicknameChangeStatus.NONE) {
                    Text(
                        text = message,
                        color = Color(0xFFF00000),
                        style = typography.H7_M_5,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 28.dp, bottom = 8.dp)
            ) {
                Text(text = "비밀번호 재설정", style = typography.H5_SB_5, color = Text4)
                if (isEmailUser) {
                    Surface(
                        shape = RoundedCornerShape(4.dp),
                        color = Button1,
                        modifier = Modifier
                            .padding(start = 6.dp)
                            .width(70.dp)
                            .height(26.dp)
                    ) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Text(text = "이메일 인증", style = typography.H10_M, color = Text4)
                        }
                    }
                }
            }

            var passwordVisible by remember { mutableStateOf(false) }
            var confirmPasswordVisible by remember { mutableStateOf(false) }

            ProfilePasswordField(
                hint = "새로운 비밀번호를 입력해주세요.",
                iconRes = if (passwordVisible) R.drawable.ic_eye_on else R.drawable.ic_eye_off,
                isVisible = passwordVisible,
                onVisibilityToggle = { passwordVisible = !passwordVisible }
            )
            Spacer(modifier = Modifier.height(20.dp))
            ProfilePasswordField(
                hint = "새로운 비밀번호를 다시 입력해주세요.",
                iconRes = if (confirmPasswordVisible) R.drawable.ic_eye_on else R.drawable.ic_eye_off,
                isVisible = confirmPasswordVisible,
                onVisibilityToggle = { confirmPasswordVisible = !confirmPasswordVisible }
            )

            Text(
                text = "사용자 유형 설정",
                style = typography.H5_SB_5,
                color = Text4,
                modifier = Modifier.padding(top = 28.dp, bottom = 8.dp)
            )
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                listOf("전체", "장애인", "임산부 및 영유아 동반").forEach { label ->
                    ProfileFacilityChip(
                        label = label,
                        isSelected = selectedUserType == label,
                        onClick = {
                            selectedUserType = label
                        }
                    )
                }
            }

            Text(
                text = "편의정보 선택",
                style = typography.H5_SB_5,
                color = Text4,
                modifier = Modifier.padding(top = 28.dp, bottom = 8.dp)
            )
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(3.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listOf(
                    "전체", "🛗 승강기", "♿ 장애인화장실", "👶 영유아동반",
                    "🛁 수유실", "# 경사로"
                ).forEach { label ->
                    ProfileFacilityChip(
                        label = label,
                        isSelected = selectedFacilities.contains(label) ||
                                (selectedFacilities.contains("전체") && label != "전체"),
                        onClick = {
                            if (label == "전체") {
                                selectedFacilities.clear()
                                selectedFacilities.add("전체")
                            } else {
                                if (selectedFacilities.contains("전체")) {
                                    selectedFacilities.remove("전체")
                                }

                                if (selectedFacilities.contains(label)) {
                                    selectedFacilities.remove(label)
                                } else {
                                    selectedFacilities.add(label)
                                }
                            }
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(70.dp))

            Button(
                onClick = {
                    val hasNicknameToChange = nicknameInput.isNotBlank()
                    val hasUserTypeToChange = isUserTypeChanged()

                    when {
                        hasNicknameToChange -> {
                            viewModel.changeNickname(nicknameInput)
                        }
                        hasUserTypeToChange -> {
                            val serverUserType = when (selectedUserType) {
                                "전체" -> "ALL"
                                "장애인" -> "DISABLED"
                                "임산부 및 영유아 동반" -> "PREGNANT"
                                else -> "ALL"
                            }
                            viewModel.updateUserType(serverUserType)
                            onNavigateToMypage()
                        }
                        isNicknameChanged -> {
                            onNavigateToMypage()
                        }
                        else -> {
                            onNavigateToMypage()
                        }
                    }
                },
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Text5,
                    contentColor = Background1
                ),
                enabled = !isLoading,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .width(20.dp)
                            .height(20.dp),
                        strokeWidth = 2.dp,
                        color = Background1
                    )
                } else {
                    Text("저장 하기", style = typography.H4_SB)
                }
            }
        }
    }
}