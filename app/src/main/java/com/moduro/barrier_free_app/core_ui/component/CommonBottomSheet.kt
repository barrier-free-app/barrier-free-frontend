package com.moduro.barrier_free_app.core_ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moduro.barrier_free_app.core_ui.theme.Background1
import com.moduro.barrier_free_app.core_ui.theme.Background2
import com.moduro.barrier_free_app.core_ui.theme.Button1
import com.moduro.barrier_free_app.core_ui.theme.Button4
import com.moduro.barrier_free_app.core_ui.theme.LocalbarrierFreeTypographyProvider
import com.moduro.barrier_free_app.core_ui.theme.MainYellow
import com.moduro.barrier_free_app.core_ui.theme.Text1
import com.moduro.barrier_free_app.core_ui.theme.Text2
import com.moduro.barrier_free_app.core_ui.theme.Text5

@Composable
private fun CommonBottomSheetContent(
    title: String,
    description: String,
    cancelText: String,
    confirmText: String,
    onCancel: () -> Unit,
    onConfirm: () -> Unit
) {
    val typography = LocalbarrierFreeTypographyProvider.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(270.dp)
            .padding(start = 20.dp, end = 20.dp, top = 5.dp, bottom = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            style = typography.H2_B,
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            modifier = Modifier.padding(top = 10.dp),
            text = description,
            style = typography.H6_M,
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.height(24.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 60.dp, bottom = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = onCancel,
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Button1),
                modifier = Modifier.weight(1f)
            ) {
                Text(cancelText, color = Text2, style = typography.H4_SB)
            }
            Button(
                onClick = onConfirm,
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Button4),
                modifier = Modifier.weight(1f)
            ) {
                Text(confirmText, color = Text1, style = typography.H4_SB)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommonBottomSheet(
    showSheet: Boolean,
    onDismissRequest: () -> Unit,
    title: String,
    description: String,
    cancelText: String = "취소",
    confirmText: String = "다음",
    onCancel: () -> Unit,
    onConfirm: (String) -> Unit,
    showWithdrawReasons: Boolean = false
) {
    var showConfirmWithdrawSheet by remember { mutableStateOf(false) }
    var selectedReason by remember { mutableStateOf("") }
    var showMainSheet by remember { mutableStateOf(showSheet) }

    if (showMainSheet) {
        ModalBottomSheet(
            onDismissRequest = {
                showMainSheet = false
                onDismissRequest()
            },
            sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
            containerColor = Color.White,
            shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        ) {
            if (showWithdrawReasons) {
                WithdrawReasonsContent(
                    onConfirm = { reason ->
                        selectedReason = reason
                        showConfirmWithdrawSheet = true
                        showMainSheet = false
                    },
                    onCancel = {
                        showMainSheet = false
                        onCancel()
                    }
                )
            } else {
                CommonBottomSheetContent(
                    title = title,
                    description = description,
                    cancelText = cancelText,
                    confirmText = confirmText,
                    onCancel = {
                        showMainSheet = false
                        onCancel()
                    },
                    onConfirm = {
                        showMainSheet = false
                        onConfirm("")
                    }
                )
            }
        }
    }

    WithdrawalConfirmBottomSheet(
        showSheet = showConfirmWithdrawSheet,
        onDismissRequest = { showConfirmWithdrawSheet = false },
        onConfirm = {
            showConfirmWithdrawSheet = false
            onConfirm(selectedReason)
        },
        policyDescription = "회원 탈퇴 시 저장된 데이터는 복구할 수 없습니다. 탈퇴하시겠습니까?"
    )
}


@Composable
fun WithdrawReasonsContent(
    onConfirm: (String) -> Unit,
    onCancel: () -> Unit,
) {
    val typography = LocalbarrierFreeTypographyProvider.current

    val initialReasons = listOf("앱에 오류가 있어요", "지도 내용과 실제 장소가 달라요", "앱 디자인이 불편해요", "선택하지 않음", "직접 작성하기")
    val reasons = remember { mutableStateListOf<String>().apply { addAll(initialReasons) } }

    var selected by remember { mutableStateOf<String?>(null) }
    var isEditing by remember { mutableStateOf(false) }
    var userInput by remember { mutableStateOf("") }

    val reasonsToShow = if (userInput.isNotBlank() && reasons.contains(userInput)) {
        reasons.filter { it != "직접 작성하기" }
    } else {
        reasons
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(700.dp)
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "탈퇴하시는 이유를 알려주세요.", style = typography.H2_B)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "여러분의 의견은 앱 개선에 많은 도움이 될 거예요!", style = typography.H9_M)
        Spacer(modifier = Modifier.height(70.dp))

        reasonsToShow.forEach { reason ->
            Surface(
                shape = RoundedCornerShape(10.dp),
                color = if (selected == reason) MainYellow else Background2,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(67.dp)
                    .padding(vertical = 4.dp)
                    .clickable {
                        if (reason == "직접 작성하기") {
                            isEditing = true
                            selected = null
                        } else {
                            selected = reason
                            isEditing = false
                        }
                    }
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = selected == reason,
                        onClick = {
                            if (reason == "직접 작성하기") {
                                isEditing = true
                                selected = null
                            } else {
                                selected = reason
                                isEditing = false
                            }
                        }
                    )
                    Spacer(modifier = Modifier.width(7.dp))
                    Text(
                        text = reason,
                        style = typography.H6_M,
                        color = Color.Unspecified
                    )
                }
            }
        }

        if (isEditing) {
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = userInput,
                onValueChange = { userInput = it },
                placeholder = { Text("이유를 입력해주세요") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .background(Background1),
                singleLine = true,
                shape = RoundedCornerShape(10.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Background1,
                    unfocusedContainerColor = Background1,
                    disabledContainerColor = Background1,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth()
            ) {
                TextButton(
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Button1,
                        contentColor = Text2,
                        disabledContainerColor = Button1,
                        disabledContentColor = Text2
                    ),
                    modifier = Modifier.weight(1f),
                    onClick = {
                        userInput = ""
                        isEditing = false
                    }
                ) {
                    Text("취소", style = typography.H4_SB)
                }

                Spacer(modifier = Modifier.width(8.dp))

                Button(
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (userInput.isNotBlank()) Color.Black else Button1,
                        contentColor = if (userInput.isNotBlank()) Color.White else Text2,
                        disabledContainerColor = Button1,
                        disabledContentColor = Text2
                    ),
                    modifier = Modifier.weight(1f),
                    onClick = {
                        if (userInput.isNotBlank()) {
                            reasons.add(reasons.size - 1, userInput)
                            selected = userInput
                            isEditing = false
                        }
                    },
                    enabled = userInput.isNotBlank()
                ) {
                    Text("확인", style = typography.H4_SB)
                }
            }

            }

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 60.dp, bottom = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = { onCancel() },
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Button1),
                modifier = Modifier.weight(1f)
            ) {
                Text(text = "취소", color = Text2, style = typography.H4_SB)
            }
            Button(
                onClick = { selected?.let { onConfirm(it) } },
                enabled = selected != null,
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (selected != null) Color.Black else Button1,
                    contentColor = if (selected != null) Color.White else Text2,
                    disabledContainerColor = Button1,
                    disabledContentColor = Text2
                ),
                modifier = Modifier.weight(1f)
            ) {
                Text(text = "다음", style = typography.H4_SB)
            }

        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WithdrawalConfirmBottomSheet(
    showSheet: Boolean,
    onDismissRequest: () -> Unit,
    onConfirm: () -> Unit,
    policyDescription: String
) {
    val typography = LocalbarrierFreeTypographyProvider.current
    if (showSheet) {
        ModalBottomSheet(
            onDismissRequest = onDismissRequest,
            sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
            containerColor = Color.White,
            shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(700.dp)
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "회원 탈퇴시 처리되는 정보",
                    style = typography.H2_B,
                    color = Text5,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = policyDescription,
                    style = typography.H6_M,
                    modifier = Modifier.padding(16.dp)
                )
                Spacer(modifier = Modifier.height(24.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 20.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = onDismissRequest,
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Button1),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(text = "취소", color = Text2, style = typography.H4_SB)
                    }
                    Button(
                        onClick = onConfirm,
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Black,
                            contentColor = Color.White
                        ),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(text = "회원 탈퇴", style = typography.H4_SB)
                    }
                }
            }
        }
    }
}


@Composable
@Preview(showBackground = true)
fun CommonBottomSheetPreview() {
    Surface {
        CommonBottomSheetContent(
            title = "로그아웃 하시겠어요?",
            description = "지금 로그아웃하면 일부 기능을 사용할 수 없어요.",
            cancelText = "취소",
            confirmText = "로그아웃",
            onCancel = {},
            onConfirm = {}
        )
    }
}
