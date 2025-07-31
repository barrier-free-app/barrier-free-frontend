package com.moduro.barrier_free_app.presentation.map.screen

import com.moduro.barrier_free_app.core_ui.component.PlaceFacilities
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.moduro.barrier_free_app.R
import com.moduro.barrier_free_app.core_ui.theme.LocalbarrierFreeTypographyProvider
import com.moduro.barrier_free_app.core_ui.theme.Text4
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.moduro.barrier_free_app.core_ui.theme.Background1
import com.moduro.barrier_free_app.core_ui.theme.Button1
import com.moduro.barrier_free_app.core_ui.theme.Text2
import com.moduro.barrier_free_app.core_ui.theme.Text3
import com.moduro.barrier_free_app.domain.entity.HomePlaceEntity
import com.moduro.barrier_free_app.domain.entity.MapPlaceSummEntity

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MapDetailComponent(
    place : MapPlaceSummEntity,
    distance : String,
    facilities : List<Int>,
    onClick: () -> Unit,
    isHeartClicked: Boolean,
    onHeartClickChanged: (Boolean) -> Unit
) {
    val typography = LocalbarrierFreeTypographyProvider.current


    var imageResource = when (place.imageType) {
        1 -> R.drawable.place_icon_parking
        2 -> R.drawable.place_icon_cultural
        3 -> R.drawable.place_icon_resturant
        4 -> R.drawable.place_icon_elavator
        5 -> R.drawable.place_icon_nursing
        else -> R.drawable.place_icon_toilet
    }



    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(color = Background1, shape = RoundedCornerShape(10.dp))
            //.clickable { onClick(id) }
    ) {

        Column (
            modifier = Modifier.padding(horizontal = 20.dp).padding(top = 22.dp)
        ){

            Row (
            ) {

                Image(
                    painter = painterResource(id = imageResource),
                    contentDescription = "",
                    modifier = Modifier.width(74.dp).height(53.dp)
                )

                Spacer(modifier = Modifier.width(16.dp))

                Column (
                    modifier = Modifier.padding(top = 5.dp)
                ){
                    Row(){

                        Text(place.name, style = typography.H5_SB_5, color = Text4)

                        Spacer(modifier = Modifier.weight(1.0f))

                        val heartIconRes = if (isHeartClicked) {
                            R.drawable.ic_heart_filled
                        } else {
                            R.drawable.ic_heart_unfilled
                        }

                        Image(
                            painter = painterResource(id = heartIconRes),
                            contentDescription = "heart icon",
                            modifier = Modifier
                                .width(20.dp).height(17.dp)
                                .clickable {
                                    onHeartClickChanged(!isHeartClicked)
                                }
                        )

                    }
                    Spacer(modifier = Modifier.height(5.dp))

                    Text(place.description , style = typography.H8_SB, color = Text2 )

                    Spacer(modifier = Modifier.height(15.dp))


                    Text("${place.address} · 거리 ${distance}km ", style = typography.H8_SB, color = Text4)


                }

            }

            Spacer(modifier = Modifier.height(14.dp))

            //카테고리 쭉 나열
            FlowRow(
                modifier = Modifier.padding(start = 90.dp),
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)){
                facilities.forEach { facility ->
                    PlaceFacilities(facility)
                }
            }

            Spacer(modifier = Modifier.height(14.dp))


            Box(
                modifier = Modifier
                    .height(41.dp)
                    .fillMaxWidth()
                    .background(color = Button1, shape = RoundedCornerShape(10.dp))
                    .clickable {
                        onClick()
                    },
                contentAlignment = Alignment.Center
            ) {
                Text("자세히 보기", style = typography.H5_M_10, color = Text3)
            }

            Spacer(modifier = Modifier.height(14.dp))



        }




    }


}

@Preview
@Composable
fun HomePlaceBoxPreview(){
    MapDetailComponent(
        MapPlaceSummEntity(
            imageType = 1,
            name = "루트205",
            address = "서울 종로구 삼청로 30",
            favorite = true,
            description = "배리어프리 시설이 있는 호텔",
            facilities = listOf(1,2),
            placeType = "map"
        ),
        distance = "3.8",
        facilities = listOf(1,2),
        {},
        true, {}
    )
}













