package com.moduro.barrier_free_app.presentation.home.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moduro.barrier_free_app.R
import com.moduro.barrier_free_app.core_ui.theme.Button1
import com.moduro.barrier_free_app.core_ui.theme.Button2
import com.moduro.barrier_free_app.core_ui.theme.LocalbarrierFreeTypographyProvider
import com.moduro.barrier_free_app.core_ui.theme.MainYellow
import com.moduro.barrier_free_app.core_ui.theme.Text4

@Composable
fun MultiSelectChip(
    type: Int,
    selected: Boolean,
    onClick: () -> Unit
) {

    val typography = LocalbarrierFreeTypographyProvider.current


    var imageResource = when (type) {
        4 -> R.drawable.home_type_mother
        1 -> R.drawable.home_type_elevator
        3 -> R.drawable.home_type_baby
        2 -> R.drawable.home_type_toilet
        5 -> R.drawable.home_type_wheelchair
        else -> null

    //경사로
    }

    var text = when (type) {
        1 -> "승강기"
        2 -> "장애인 화장실"
        3 -> "영유아 동반"
        4 -> "수유실"
        5 -> "경사로"
        else -> "전체"
    }

    var bgColor = if (selected) MainYellow else Button1

    Box(
        modifier = Modifier.wrapContentWidth().height(32.dp)
            .background(color = bgColor, shape = RoundedCornerShape(8.2.dp))
            .clickable { onClick() }
    ){
        Row(
            modifier = Modifier.padding(horizontal = 7.dp).padding(vertical = 7.dp)
        ){
            imageResource?.let {
                Image(
                    painter = painterResource(id = it),
                    contentDescription = "",
                    modifier = Modifier
                        .height(17.dp)

                )

                Spacer(modifier = Modifier.width(4.dp))
            }

            Text(text, style = typography.H7_M_10, color = Text4)

        }

    }


}

@Preview(showBackground = true)
@Composable
fun PreviewMultiSelectChip() {
    MultiSelectChip(
        type = 1,
        selected = true,
        onClick = {}
    )
}
