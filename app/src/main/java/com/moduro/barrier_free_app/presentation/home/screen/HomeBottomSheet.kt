package com.moduro.barrier_free_app.presentation.home.screen

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeBottomSheet(
    showSheet: Boolean,
    onDismiss: () -> Unit,
    onConfirm: (singleSelection: String, multiSelections: List<Int>) -> Unit
) {
    // 바텀시트 상태
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    if (showSheet) {
        ModalBottomSheet(
            onDismissRequest = onDismiss,
            sheetState = sheetState,
            modifier = Modifier.fillMaxWidth(), containerColor = Color.White
        ) {
            FilterContent(
                onDismiss = onDismiss,
                onConfirm = onConfirm
            )
        }
    }
}

@Preview
@Composable
fun PreviewFilterHomeSheet() {
    HomeBottomSheet(
        showSheet = true,
        onDismiss = {},
        onConfirm = { single, multi -> }
    )
}