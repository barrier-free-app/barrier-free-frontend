package com.moduro.barrier_free_app.presentation.auth.screen

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
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.moduro.barrier_free_app.core_ui.component.CommonTopBar
import com.moduro.barrier_free_app.core_ui.component.ProfileFacilityChip
import com.moduro.barrier_free_app.core_ui.component.ProfileNicknameField
import com.moduro.barrier_free_app.core_ui.component.SignUpTextField
import com.moduro.barrier_free_app.core_ui.theme.Background1
import com.moduro.barrier_free_app.core_ui.theme.Background2
import com.moduro.barrier_free_app.core_ui.theme.LocalbarrierFreeTypographyProvider
import com.moduro.barrier_free_app.core_ui.theme.Text4
import com.moduro.barrier_free_app.core_ui.theme.Text5
import com.moduro.barrier_free_app.presentation.auth.navigation.AuthNavigator

@Composable
fun SignUpSettingRoute(
    navigator: AuthNavigator,
    viewModel: SignUpViewModel = viewModel()
) {
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
    var nicknameInput by remember { mutableStateOf("") }
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
                    viewModel.setNickname(nicknameInput)
                }
            )

            Spacer(modifier = Modifier.height(40.dp))

            SignUpTextField(
                label = "아이디",
                hint = "아이디(영문+숫자 6~16자)",
                text = viewModel.id,
                onValueChange = {
                    viewModel.id = it
                },
                enabled = true
            )

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
                                (selectedUserTypes.contains("전체") && label != "전체"),
                        onClick = { toggleSelection(selectedUserTypes, label) }
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
                                (selectedFacilities.contains("전체") && label != "전체"),
                        onClick = { toggleSelection(selectedFacilities, label) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(70.dp))

            Button(
                onClick = { onNavigateToMain() },
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


fun toggleSelection(list: SnapshotStateList<String>, item: String) {
    if (item == "전체") {
        list.clear()
        list.add("전체")
    } else {
        if (list.contains("전체")) list.remove("전체")

        if (list.contains(item)) list.remove(item)
        else list.add(item)
    }
}
