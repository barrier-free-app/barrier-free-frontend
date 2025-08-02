package com.moduro.barrier_free_app.core_ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moduro.barrier_free_app.R
import com.moduro.barrier_free_app.core_ui.theme.Background1
import com.moduro.barrier_free_app.core_ui.theme.LocalbarrierFreeTypographyProvider
import com.moduro.barrier_free_app.domain.entity.FavoritePlaceEntity

@Composable
fun FavoritePlaceCard(
    place: FavoritePlaceEntity,
    onRemoveClick: () -> Unit
) {
    val typography = LocalbarrierFreeTypographyProvider.current
    val imageRes = when (place.imageType) {
        0 -> R.drawable.saved_placeholder_parking
        1 -> R.drawable.saved_placeholder_culture
        2 -> R.drawable.saved_placeholder_restaurant
        3 -> R.drawable.saved_placeholder_elevator
        4 -> R.drawable.saved_placeholder_nursing_room
        else -> R.drawable.saved_placeholder_toilet
    }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(240.dp)
            .padding(5.dp),
        shape = RoundedCornerShape(10.dp),
        color = Background1
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
        ) {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(110.dp)
                    .clip(RoundedCornerShape(10.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = place.name,
                    style = typography.H5_SB_5,
                    modifier = Modifier.weight(1f)
                )

                Image(
                    painter = painterResource(
                        id = if (place.favorite) R.drawable.ic_heart_filled
                        else R.drawable.ic_heart_unfilled
                    ),
                    contentDescription = null,
                    modifier = Modifier
                        .size(24.dp)
                        .clickable {
                            onRemoveClick()
                        }
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = place.description,
                style = typography.H7_M_5,
                color = Color.Gray
            )
        }
    }
}

@Preview()
@Composable
fun FavoritePlaceCardPreview() {
    FavoritePlaceCard(
        place = FavoritePlaceEntity(
            id = 1L,
            type = "map",
            name = "서울 공원",
            description = "장애인 접근성이 좋은 공원입니다.",
            facilities = listOf(1, 3),
            imageType = 2,
            favorite = true
        ),
        onRemoveClick = {}
    )
}