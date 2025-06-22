package com.moduro.barrier_free_app.core_ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moduro.barrier_free_app.R
import com.moduro.barrier_free_app.core_ui.theme.LocalbarrierFreeTypographyProvider
import com.moduro.barrier_free_app.core_ui.theme.Text4

@Composable
fun HomeWeatherBox(
    weatherType: Int,
    dustType: Int,
    location: String,
    temp: String,
    isLargeTextMode: Boolean

) {
    val typography = LocalbarrierFreeTypographyProvider.current

    var weatherText = when (weatherType) {
        1 -> "맑고 "
        2 -> "흐리고 "
        else -> "비가 오고 "
    }

    var dustText = when (dustType) {
        1 -> "미세먼지가 심하"
        2 -> "미세먼지는 좋아"
        else -> "미세먼지는 보통"
    }

    var dust = when (dustType) {
        1 -> "나쁨"
        2 -> "좋음"
        else -> "보통"
    }

    var fullText = weatherText + dustText


    var imageResource = when (weatherType) {
        1 -> R.drawable.weather_clean_background
        2 -> R.drawable.weather_50clean_background
        else -> R.drawable.weather_0clean_background
    }

    var iconResource = when (weatherType) {
        1 -> R.drawable.weather_sun
        2 -> R.drawable.weather_cloud
        else -> R.drawable.weather_rain
    }

    Box(
        modifier = Modifier
            .width(380.dp)
            .height(if (isLargeTextMode) 188.dp else 169.dp)
    ) {
        Image(
            painter = painterResource(id = imageResource), // XML or PNG/JPG 이미지
            contentDescription = "",
            modifier = Modifier.fillMaxSize(), contentScale = ContentScale.FillBounds
        )

        Column(
            modifier = Modifier
                .offset(x = 19.dp, y = 17.dp)
                .padding(end = 19.dp)
        ) {
            Row {
                Image(
                    painter = painterResource(id = R.drawable.weather_location), // XML or PNG/JPG 이미지
                    contentDescription = "",
                )

                Spacer(modifier = Modifier.width(7.dp))

                Text(location, style = typography.H8_SB, color = Text4)
            }

            Spacer(modifier = Modifier.height(21.dp))

            Text(
                text = buildAnnotatedString {
                    append("오늘 날씨는 ")
                    withStyle(style = typography.H5_B_5.toSpanStyle().copy(color = Text4)) {
                        append(fullText)
                    }
                    if (dustType == 1) {
                        append("네요.")
                    } else if (dustType == 2) {
                        append("요.")
                    } else {
                        append("이예요.")
                    }
                },
                style = typography.H5_SB_5, color = Text4
            )

            if (dustType == 1 || weatherType == 3) {
                Text(
                    text = buildAnnotatedString {
                        append("가급적 실외보다 ")
                        withStyle(style = typography.H5_B_5.toSpanStyle().copy(color = Text4)) {
                            append("실내 활동")
                        }
                        append("을 추천드릴게요.")
                    },
                    style = typography.H5_SB_5
                )
            } else {
                Text(
                    text = "바깥 활동을 하기에 적당할 것 같네요!",
                    style = typography.H5_SB_5,
                    color = Text4
                )
            }


            Spacer(modifier = Modifier.height(21.dp))

            Row {

                Image(
                    painter = painterResource(id = iconResource), // XML or PNG/JPG 이미지
                    contentDescription = "",
                )

                Spacer(modifier = Modifier.width(30.dp))


                Text("현재 기온", style = typography.H7_M_5, color = Text4.copy(alpha = 0.6f))

                Spacer(modifier = Modifier.width(4.dp))

                Text(temp + "℃", style = typography.H7_M_5, color = Text4)

                Spacer(modifier = Modifier.width(29.dp))


                Text("미세먼지", style = typography.H7_M_5, color = Text4.copy(alpha = 0.6f))

                Spacer(modifier = Modifier.width(4.dp))

                Text(dust, style = typography.H7_M_5, color = Text4)

            }

        }


    }

}

@Composable
@Preview
fun weatherBoxPreview() {

    HomeWeatherBox(
        1, 2, "서울특별시 용산구", "10", true
    )

}