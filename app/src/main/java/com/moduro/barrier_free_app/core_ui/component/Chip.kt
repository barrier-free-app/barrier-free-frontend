package com.moduro.barrier_free_app.core_ui.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.moduro.barrier_free_app.core_ui.theme.Background1
import com.moduro.barrier_free_app.core_ui.theme.Button1
import com.moduro.barrier_free_app.core_ui.theme.Text5

@Composable
fun FacilityChip(
    label: String
) {
    Surface(
        shape = RoundedCornerShape(10.dp),
        color = Button1,
        modifier = Modifier
            .fillMaxWidth()
            .padding(end = 8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
        ) {
            Text(
                text = label,
                fontSize = 14.sp,
                color = Text5
            )
        }
    }
}

@Composable
fun MypageFacilityChip(
    label: String
) {
    Surface(
        shape = RoundedCornerShape(10.dp),
        color = Background1,
        modifier = Modifier
            .fillMaxWidth()
            .padding(end = 8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
        ) {
            Text(
                text = label,
                fontSize = 14.sp,
                color = Text5
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun FacilityChipPreview() {
    FacilityChip(
        label = "🛗 엘리베이터"
    )
}
