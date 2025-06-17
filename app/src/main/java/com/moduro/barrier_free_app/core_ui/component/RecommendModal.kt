package com.moduro.barrier_free_app.core_ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.moduro.barrier_free_app.R
import com.moduro.barrier_free_app.core_ui.theme.LocalbarrierFreeTypographyProvider
import com.moduro.barrier_free_app.core_ui.theme.Text4
import kotlinx.coroutines.delay

enum class RecommendType {
    THUMB_UP, THUMB_DOWN
}

@Composable
fun RecommendModal(
    type: RecommendType,
    onTimeout: () -> Unit
) {
    val message = "의견을 남겨주셔서 감사합니다!"
    val iconRes = when (type) {
        RecommendType.THUMB_UP -> R.drawable.ic_thumbup_selected
        RecommendType.THUMB_DOWN -> R.drawable.ic_thumbdown_selected
    }

    Dialog(onDismissRequest = {}) {
        Surface(
            modifier = Modifier
                .width(380.dp)
                .padding(horizontal = 16.dp, vertical = 10.dp),
            shape = androidx.compose.foundation.shape.RoundedCornerShape(16.dp),
            color = androidx.compose.ui.graphics.Color.White
        ) {
            Column(
                modifier = Modifier.padding(vertical = 24.dp, horizontal = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = message,
                    style = LocalbarrierFreeTypographyProvider.current.H6_M,
                    color = Text4
                )
                Spacer(modifier = Modifier.height(16.dp))
                Image(
                    painter = painterResource(id = iconRes),
                    contentDescription = "붐업/붐따 아이콘",
                    modifier = Modifier.size(32.dp)
                )
            }
        }
    }

    LaunchedEffect(type) {
        delay(1000)
        onTimeout()
    }
}
