package com.moduro.barrier_free_app.presentation.map.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moduro.barrier_free_app.core_ui.theme.Button1
import com.moduro.barrier_free_app.core_ui.theme.LocalbarrierFreeTypographyProvider
import com.moduro.barrier_free_app.core_ui.theme.Text2
import com.moduro.barrier_free_app.presentation.home.screen.MultiSelectChip
import com.moduro.barrier_free_app.presentation.home.screen.SingleSelectChip


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MapFilterContent(
    onDismiss: () -> Unit,
    onConfirm: (List<Int>) -> Unit
) {

    val typography = LocalbarrierFreeTypographyProvider.current

    val multiOptions = listOf(0, 1, 2, 3, 4, 5)
    val multiSelected = remember { mutableStateListOf<Int>() }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(25.dp)
    ) {

        Text(
            text = "필터링 설정",
            style = typography.H2_B,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(Modifier.height(44.dp))



        // 복수 선택 그룹
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            multiOptions.forEach { option ->
                MultiSelectChip(
                    type = option,
                    selected = multiSelected.contains(option),
                    onClick = {
                        if (option == 0) {
                            multiSelected.clear()
                            multiSelected.add(0)
                        } else {
                            if (multiSelected.contains(option)) {
                                multiSelected.remove(option)
                            } else {
                                multiSelected.add(option)
                            }
                            // '전체' 선택 해제
                            if (multiSelected.contains(0)) {
                                multiSelected.remove(0)
                            }
                            // 아무것도 없으면 다시 전체
                            if (multiSelected.isEmpty()) {
                                multiSelected.add(0)
                            }
                        }
                    }
                )
            }
        }

        Spacer(Modifier.height(30.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp), // 버튼 높이 고정
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .background(color = Button1, shape = RoundedCornerShape(10.dp))
                    .clickable { onDismiss() },
                contentAlignment = Alignment.Center
            ) {
                Text("취소", style = typography.H5_SB_10, color = Text2)
            }

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .background(color = Color.Black, shape = RoundedCornerShape(10.dp))
                    .clickable {
                        onConfirm(multiSelected.toList())
                    },
                contentAlignment = Alignment.Center
            ) {
                Text("확인", style = typography.H5_SB_10, color = Color.White)
            }
        }
    }
}

@Preview
@Composable
fun MapFilterContentPreview(){

    MapFilterContent(
        {}, {}
    )
}