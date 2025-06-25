package com.moduro.barrier_free_app.core_ui.component

import android.provider.ContactsContract.Profile
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
import com.moduro.barrier_free_app.core_ui.theme.Button2
import com.moduro.barrier_free_app.core_ui.theme.LocalbarrierFreeTypographyProvider
import com.moduro.barrier_free_app.core_ui.theme.MainYellow
import com.moduro.barrier_free_app.core_ui.theme.Text1
import com.moduro.barrier_free_app.core_ui.theme.Text4
import com.moduro.barrier_free_app.core_ui.theme.Text5

@Composable
fun FacilityChip(
    label: String
) {
    val typography = LocalbarrierFreeTypographyProvider.current
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
                style = typography.H7_M_10,
                color = Text4
            )
        }
    }
}

@Composable
fun MypageFacilityChip(
    label: String
) {
    val typography = LocalbarrierFreeTypographyProvider.current
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
                style = typography.H7_M_10,
                color = Text4
            )
        }
    }
}

@Composable
fun ProfileFacilityChip(
    label: String,
    isSelected: Boolean = false,
    onClick: () -> Unit
) {
    val backgroundColor = if (isSelected) MainYellow else Button1
    val typography = LocalbarrierFreeTypographyProvider.current
    Surface(
        shape = RoundedCornerShape(10.dp),
        color = backgroundColor,
        onClick = onClick,
        modifier = Modifier.padding(end = 8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
        ) {
            Text(
                text = label,
                style = typography.H7_M_10,
                color = Text4
            )
        }
    }
}

@Composable
fun FavoriteFacilityChip(
    label: String,
    isSelected: Boolean = false,
    onClick: () -> Unit
) {
    val backgroundColor = if (isSelected) Button2 else Button1
    val textColor = if (isSelected) Text1 else Text4
    val typography = LocalbarrierFreeTypographyProvider.current
    Surface(
        shape = RoundedCornerShape(10.dp),
        color = backgroundColor,
        onClick = onClick,
        modifier = Modifier.padding(end = 8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
        ) {
            Text(
                text = label,
                style = typography.H7_M_10,
                color = textColor
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
