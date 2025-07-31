package com.moduro.barrier_free_app.presentation.auth.screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.moduro.barrier_free_app.core_ui.component.CommonTopBar
import com.moduro.barrier_free_app.core_ui.component.IdDuplicateTextField
import com.moduro.barrier_free_app.core_ui.component.ProfileFacilityChip
import com.moduro.barrier_free_app.core_ui.component.ProfileNicknameField
import com.moduro.barrier_free_app.core_ui.component.SignUpTextField
import com.moduro.barrier_free_app.core_ui.theme.Background1
import com.moduro.barrier_free_app.core_ui.theme.Background2
import com.moduro.barrier_free_app.core_ui.theme.LocalbarrierFreeTypographyProvider
import com.moduro.barrier_free_app.core_ui.theme.Text4
import com.moduro.barrier_free_app.core_ui.theme.Text5
import com.moduro.barrier_free_app.presentation.auth.navigation.AuthNavigator
import com.moduro.barrier_free_app.presentation.mypage.screen.NicknameChangeStatus

@Composable
fun SignUpSettingRoute(
    navigator: AuthNavigator,
    viewModel: SignUpViewModel = hiltViewModel(),
    email: String
) {
    LaunchedEffect(Unit) {
        viewModel.setEmail(email)
    }

    SignUpSettingScreen(
        onBackClick = { navigator.navController.popBackStack() },
        onNavigateToMain = { navigator.navigateToMain() },
        viewModel = viewModel
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SignUpSettingScreen(
    onBackClick: () -> Unit,
    onNavigateToMain: () -> Unit,
    viewModel: SignUpViewModel
) {

    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.setSystemBarsColor(color = Background2)
    }

    val typography = LocalbarrierFreeTypographyProvider.current
    val current = LocalContext.current

    val nicknameChangeStatus by viewModel.nicknameChangeStatus.collectAsState()
    val idStatus by viewModel.idStatus.collectAsState()

    var nicknameInput by remember { mutableStateOf("") }
    var idInput by remember { mutableStateOf("") }

    val userInfo = viewModel.userInfo.collectAsState().value

    val selectedUserTypes = remember {
        mutableStateListOf<String>().apply {
            userInfo?.userType?.let { add(it) }
        }
    }
    val selectedFacilities = remember {
        mutableStateListOf<String>().apply {
            userInfo?.userFacilities?.forEach { id -> add(id.toString()) }
        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background2)
    ) {
        CommonTopBar(title = "회원가입", onBackClick = onBackClick)

        Column(modifier = Modifier.padding(horizontal = 20.dp)) {

            Text(
                text = "기본정보",
                style = typography.H5_SB_5,
                color = Text4,
                modifier = Modifier.padding(top = 20.dp, bottom = 8.dp)
            )
            ProfileNicknameField(
                hint = "닉네임을 입력해주세요",
                text = nicknameInput,
                onValueChange = { nicknameInput = it },
                onCheckDuplicateClick = {
                    viewModel.checkDuplicate(
                        type = "nickname",
                        input = nicknameInput,
                        onSuccess = { viewModel.setNicknameChangeStatus(NicknameChangeStatus.SUCCESS) },
                        onFailure = { viewModel.setNicknameChangeStatus(NicknameChangeStatus.DUPLICATE) }
                    )
                }
            )

            Spacer(modifier = Modifier.height(10.dp))

            when (nicknameChangeStatus) {
                NicknameChangeStatus.SUCCESS -> {
                    Text(
                        text = "사용 가능한 닉네임 입니다.",
                        color = Color(0xFF023DFF),
                        style = typography.H7_M_5,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }

                NicknameChangeStatus.DUPLICATE -> {
                    Text(
                        text = "이미 존재하는 닉네임 입니다.",
                        color =  Color(0xFFF00000),
                        style = typography.H7_M_5,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }

                else -> {}
            }

            Spacer(modifier = Modifier.height(40.dp))

            IdDuplicateTextField(
                label = "아이디",
                hint = "아이디(영문+숫자 6~16자)",
                text = idInput,
                onValueChange = {
                    idInput = it
                    viewModel.id = it
                },
                onCheckDuplicateClick = {
                    viewModel.checkDuplicate(
                        type = "username",
                        input = idInput,
                        onSuccess = { viewModel.setIdStatus(NicknameChangeStatus.SUCCESS) },
                        onFailure = { viewModel.setIdStatus(NicknameChangeStatus.DUPLICATE) }
                    )
                },
                enabled = true
            )

            Spacer(modifier = Modifier.height(10.dp))

            when (idStatus) {
                NicknameChangeStatus.SUCCESS -> {
                    Text(
                        text = "사용 가능한 아이디 입니다.",
                        color = Color(0xFF023DFF),
                        style = typography.H7_M_5,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }

                NicknameChangeStatus.DUPLICATE -> { //TODO 유효 길이 준수, 형식 준수 등 예외 처리 추가
                    Text(
                        text = "이미 존재하는 아이디 입니다",
                        color =  Color(0xFFF00000),
                        style = typography.H7_M_5,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }

                else -> {}
            }

            Spacer(modifier = Modifier.height(10.dp))

            SignUpTextField(
                label = "비밀번호",
                hint = "비밀번호(영문+숫자 6~16자)",
                text = viewModel.password,
                onValueChange = {
                    viewModel.password = it
                },
                enabled = true
            )

            Spacer(modifier = Modifier.height(40.dp))

            // TODO 사용자 미 선택시 선택하도록 막기
            Text(
                text = "사용자 유형 설정",
                style = typography.H5_SB_5,
                color = Text4,
                modifier = Modifier.padding(top = 28.dp, bottom = 8.dp)
            )
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                listOf("전체", "휠체어 사용자", "영유아 동반").forEach { label ->
                    ProfileFacilityChip(
                        label = label,
                        isSelected = selectedUserTypes.contains(label) ||
                                (selectedUserTypes.contains("전체") &&
                                        label in listOf("휠체어 사용자", "영유아 동반"))
                        ,
                        onClick = { toggleUserTypeSelection(selectedUserTypes, label) }
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
                    "🛁 수유실", "🧑‍🦽경사로"
                ).forEach { label ->
                    ProfileFacilityChip(
                        label = label,
                        isSelected = selectedFacilities.contains(label) ||
                                (selectedFacilities.contains("전체") && label in facilityLabels)
                        ,
                        onClick = { toggleFacilitySelection(selectedFacilities, label) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(45.dp))

            Button(
                onClick = {
                    val mappedUserType = when {
                        selectedUserTypes.contains("전체") ||
                                selectedUserTypes.containsAll(listOf("휠체어 사용자", "영유아 동반")) -> "ALL"
                        selectedUserTypes.contains("휠체어 사용자") -> "DISABLED"
                        selectedUserTypes.contains("영유아 동반") -> "PREGNANT"
                        else -> "ALL"
                    }


                    viewModel.submitSignUpSetting(
                        email = viewModel.inputEmail,
                        nickname = nicknameInput,
                        username = viewModel.id,
                        password = viewModel.password,
                        verifyPassword = viewModel.password,
                        userType = mappedUserType,
                        userFacilityIds = if (selectedFacilities.contains("전체")) {
                            listOf(1, 2, 3, 4, 5)
                        } else {
                            selectedFacilities.mapNotNull { labelToFacilityId(it) }
                        },
                        onSuccess = { onNavigateToMain() },
                        onFailure = { message ->
                            Toast
                                .makeText(current, message, Toast.LENGTH_SHORT)
                                .show()
                        }
                    )
                },
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Text5,
                    contentColor = Background1
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
            ) {
                Text("가입 완료", style = typography.H4_SB)
            }
        }
    }
}


fun toggleUserTypeSelection(list: SnapshotStateList<String>, item: String) {
    if (item == "전체") {
        list.clear()
        list.add("전체")
    } else {
        list.remove("전체")
        if (list.contains(item)) list.remove(item) else list.add(item)

        // 조건: 둘 다 선택되었으면 '전체'로 간주
        if (list.contains("휠체어 사용자") && list.contains("영유아 동반")) {
            list.clear()
            list.add("전체")
        }
    }
}

val facilityLabels = listOf("🛗 승강기", "♿ 장애인화장실", "👶 영유아동반", "🛁 수유실", "🧑‍🦽경사로")

fun toggleFacilitySelection(list: SnapshotStateList<String>, item: String) {
    if (item == "전체") {
        list.clear()
        list.addAll(listOf("전체") + facilityLabels)
    } else {
        list.remove("전체")
        if (list.contains(item)) list.remove(item) else list.add(item)

        // 모든 항목 선택되면 '전체' 추가
        if (facilityLabels.all { list.contains(it) }) {
            if (!list.contains("전체")) list.add("전체")
        } else {
            list.remove("전체")
        }
    }
}

fun labelToFacilityId(label: String): Int? {
    return when (label) {
        "🛗 승강기" -> 1
        "♿ 장애인화장실" -> 2
        "👶 영유아동반" -> 3
        "🛁 수유실" -> 4
        "🧑‍🦽경사로" -> 5
        else -> null
    }
}

