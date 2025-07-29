package com.moduro.barrier_free_app.presentation.mypage.screen


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moduro.barrier_free_app.core_ui.component.PlaceReportTypeChip
import com.moduro.barrier_free_app.core_ui.theme.LocalbarrierFreeTypographyProvider
import com.moduro.barrier_free_app.presentation.home.screen.MultiSelectChip

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PlaceReportFilterContent(
    valueType: Int,
    onValueChange: (List<Int>) -> Unit
) {
    val multiOptions = listOf(0, 1, 2, 3, 4, 5)
    val multiSelected = remember { mutableStateListOf(0) }

    val singleOptions = listOf(0, 1, 2, 3, 4, 5)
    var singleSelected by remember { mutableStateOf(0) }

    if (valueType == 1) {
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
                        onValueChange(multiSelected.toList())
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
                    type = option,
                    selected = singleSelected == option,
                    onClick = {
                        singleSelected = option
                        onValueChange(listOf(option))  // 리스트로 감싸서 전달
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