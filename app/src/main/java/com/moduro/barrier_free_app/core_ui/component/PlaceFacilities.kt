package com.moduro.barrier_free_app.core_ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moduro.barrier_free_app.R
import com.moduro.barrier_free_app.core_ui.theme.Button1
import com.moduro.barrier_free_app.core_ui.theme.LocalbarrierFreeTypographyProvider
import com.moduro.barrier_free_app.core_ui.theme.Text4

@Composable
fun PlaceFacilities (
    type : String
){

    val typography = LocalbarrierFreeTypographyProvider.current


    var imageResource = when (type) {
        "수유실" -> R.drawable.home_type_mother
        "승강기" -> R.drawable.home_type_elevator
        "영유아 동반" -> R.drawable.home_type_baby
        "장애인 화장실" -> R.drawable.home_type_toilet
        else -> R.drawable.home_type_wheelchair
        //경사로
    }

    Box(
        modifier = Modifier.wrapContentWidth().height(25.dp)
            .background(color = Button1, shape = RoundedCornerShape(8.2.dp)),
    ){
        Row(
            modifier = Modifier.padding(end = 6.5.dp).padding(start = 5.5.dp).padding(vertical = 4.dp)
        ){
            Image(
                painter = painterResource(id = imageResource),
                contentDescription = "",
                modifier = Modifier.height(14.dp).width(18.dp)
            )

            Spacer(modifier = Modifier.width(2.dp))

            Text(type, style = typography.H10_M, color = Text4)

        }

    }



}

@Preview
@Composable
fun PlaceFacilitiesPreview(){

    PlaceFacilities("경사로")
}