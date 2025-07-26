package com.moduro.barrier_free_app.presentation.mypage.screen


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moduro.barrier_free_app.core_ui.component.PlaceReportTypeChip
import com.moduro.barrier_free_app.core_ui.theme.LocalbarrierFreeTypographyProvider
import com.moduro.barrier_free_app.presentation.home.screen.MultiSelectChip


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PlaceReportFilterContent(
    valueType: Int,
    onValueChange: (List<String>) -> Unit
) {

    val typography = LocalbarrierFreeTypographyProvider.current

    val multiOptions = listOf("전체", "승강기", "장애인 화장실", "영유아 동반", "수유실", "경사로")
    val multiSelected = remember { mutableStateListOf<String>("전체") }

    val singleOptions = listOf("주차장", "문화시설", "식당", "승강기", "수유실", "화장실")
    val singleSelected = remember { mutableStateListOf<String>() }


    if (valueType == 1) {
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            multiOptions.forEach { option ->
                MultiSelectChip(
                    text = option,
                    selected = multiSelected.contains(option),
                    onClick = {
                        if (option == "전체") {
                            // 전체 선택 시, 다른 건 모두 해제하고 전체만 선택
                            multiSelected.clear()
                            multiSelected.add("전체")
                        } else {
                            if (multiSelected.contains(option)) {
                                multiSelected.remove(option)
                            } else {
                                multiSelected.add(option)
                            }
                            // '전체' 선택 상태 해제
                            if (multiSelected.contains("전체")) {
                                multiSelected.remove("전체")
                            }
                            // 아무것도 선택 안 됐으면 '전체' 자동 선택
                            if (multiSelected.isEmpty()) {
                                multiSelected.add("전체")
                            }
                        }
                    }
                )
            }
        }
    } else {
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            singleOptions.forEach { option ->
                PlaceReportTypeChip(
                    text = option,
                    selected = singleSelected.contains(option),
                    onClick = {
                        singleSelected.clear()
                        singleSelected.add(option)
                        onValueChange(singleSelected.toList())

                    }
                )
            }
        }


    }


}

@Preview
@Composable
fun PlaceReportFilterContentPreview() {

    PlaceReportFilterContent(
        1, {}
    )
}