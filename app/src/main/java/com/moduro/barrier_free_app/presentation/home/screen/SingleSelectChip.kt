package com.moduro.barrier_free_app.presentation.home.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moduro.barrier_free_app.R
import com.moduro.barrier_free_app.core_ui.theme.Background1
import com.moduro.barrier_free_app.core_ui.theme.Background2
import com.moduro.barrier_free_app.core_ui.theme.Button1
import com.moduro.barrier_free_app.core_ui.theme.LocalbarrierFreeTypographyProvider

@Composable
fun SingleSelectChip(
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    val typography = LocalbarrierFreeTypographyProvider.current

    var imageResource = if (selected) R.drawable.circle_selected else R.drawable.circle_unselected

    // 네모 박스 + 텍스트 앞 동그라미
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .height(57.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(if (selected) Button1 else Background2)
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 10.dp)
    ) {
        // 동그라미 표시
        Image(
            painter = painterResource(id = imageResource),
            contentDescription = "",
            //modifier = Modifier.width(96.dp).height(81.06.dp)
        )

        Spacer(Modifier.width(11.dp))
        Text(text = text, style = typography.H6_M)
    }
}

@Preview
@Composable
fun PreviewSingleSelectChip() {
    SingleSelectChip(
        text = "가까운 거리의 장소를 추천받고 싶어요",
        selected = false,
        onClick = {}
    )
}
