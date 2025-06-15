package com.moduro.barrier_free_app.core_ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moduro.barrier_free_app.core_ui.theme.Button1
import com.moduro.barrier_free_app.core_ui.theme.Button4
import com.moduro.barrier_free_app.core_ui.theme.LocalbarrierFreeTypographyProvider
import com.moduro.barrier_free_app.core_ui.theme.Text1
import com.moduro.barrier_free_app.core_ui.theme.Text2

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
    onConfirm: () -> Unit
) {
    if (showSheet) {
        ModalBottomSheet(
            onDismissRequest = onDismissRequest,
            sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
            containerColor = Color.White,
            shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        ) {
            CommonBottomSheetContent(
                title = title,
                description = description,
                cancelText = cancelText,
                confirmText = confirmText,
                onCancel = onCancel,
                onConfirm = onConfirm
            )
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
