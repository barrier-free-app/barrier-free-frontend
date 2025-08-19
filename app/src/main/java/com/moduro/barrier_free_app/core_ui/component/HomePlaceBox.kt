package com.moduro.barrier_free_app.core_ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moduro.barrier_free_app.R
import com.moduro.barrier_free_app.core_ui.theme.Background1
import com.moduro.barrier_free_app.core_ui.theme.LocalbarrierFreeTypographyProvider
import com.moduro.barrier_free_app.core_ui.theme.SubYellow
import com.moduro.barrier_free_app.core_ui.theme.Text4
import com.moduro.barrier_free_app.data.dto.response.ResponseHomeHotPlaceDto
import com.moduro.barrier_free_app.domain.entity.HomePlaceEntity

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun HomePlaceBox(
    place : HomePlaceEntity,
    onClick: (Long) -> Unit
) {
    val typography = LocalbarrierFreeTypographyProvider.current


    var imageResource = when (place.imageType) {
        0 -> R.drawable.place_icon_parking
        1 -> R.drawable.place_icon_cultural
        2 -> R.drawable.place_icon_resturant
        3 -> R.drawable.place_icon_elavator
        4 -> R.drawable.place_icon_nursing
        else -> R.drawable.place_icon_toilet
    }

    var bgColor = when (place.placeType){
        "map" -> Background1
        else -> SubYellow
    }



    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(color = bgColor, shape = RoundedCornerShape(10.dp))
            .clickable { onClick(place.placeId) }
    ) {
        Row (
            modifier = Modifier.padding(horizontal = 11.dp).padding(vertical = 10.dp)
        ) {

            Image(
                painter = painterResource(id = imageResource),
                contentDescription = "",
                modifier = Modifier.width(96.dp).height(81.06.dp)
            )

            Spacer(modifier = Modifier.width(14.dp))

            Column (
                modifier = Modifier.padding(top = 5.dp)
            ){
                Row(){

                    Text(place.name, style = typography.H9_M, color = Text4)

                    Spacer(modifier = Modifier.weight(1.0f))

                    Text(place.region, style = typography.H10_M, color = Text4)

                }

                Spacer(modifier = Modifier.height(5.dp))

                Text(place.description, style = typography.H9_M, color = Text4.copy(alpha = 0.5f))

                Spacer(modifier = Modifier.height(12.dp))

                //카테고리 쭉 나열
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)){
                    place.facility.forEach { fac ->
                        PlaceFacilities(fac)
                    }
                }


            }



        }


    }


}

@Preview
@Composable
fun HomePlaceBoxPreview(){
    HomePlaceBox(
        HomePlaceEntity(
            placeId = 1,
            placeType = "map",
            name = "종로 맛집",
            region = "종로구",
            description = "종로구의 유명한 맛집",
            facility = listOf(1,2,3),
            imageType = 1

        ),
        {}
    )
}













