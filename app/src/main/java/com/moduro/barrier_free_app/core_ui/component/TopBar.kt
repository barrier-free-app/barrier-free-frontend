package com.moduro.barrier_free_app.core_ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moduro.barrier_free_app.R
import com.moduro.barrier_free_app.core_ui.theme.LocalbarrierFreeTypographyProvider

@Composable
fun CommonTopBar(
    title: String,
    onBackClick: () -> Unit
) {
    val typography = LocalbarrierFreeTypographyProvider.current
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .padding(horizontal = 10.dp, vertical = 20.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = title,
            style = typography.H3_B
        )

        Image(
            painter = painterResource(id = R.drawable.ic_back),
            contentDescription = "Back",
            modifier = Modifier
                .align(Alignment.CenterStart)
                .size(24.dp)
                .clickable { onBackClick() }
        )
    }
}


@Preview(showBackground = true)
@Composable
fun CommonTopBarPreview() {
    CommonTopBar(
        title = "플레이스 상세 정보",
        onBackClick = { }
    )
}
