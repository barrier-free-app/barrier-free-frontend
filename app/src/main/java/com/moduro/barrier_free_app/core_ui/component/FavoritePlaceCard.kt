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
import androidx.compose.ui.unit.dp
import com.moduro.barrier_free_app.R
import com.moduro.barrier_free_app.core_ui.theme.Background1
import com.moduro.barrier_free_app.core_ui.theme.LocalbarrierFreeTypographyProvider
import com.moduro.barrier_free_app.presentation.mypage.screen.FavoritePlace


@Composable
fun FavoritePlaceCard(
    place: FavoritePlace,
    onRemoveClick: () -> Unit
) {
    val typography = LocalbarrierFreeTypographyProvider.current

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
                painter = painterResource(id = place.imageRes),
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
                        id = if (place.isLiked) R.drawable.ic_heart_filled
                        else R.drawable.ic_heart_unfilled
                    ),
                    contentDescription = "좋아요",
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
