package com.moduro.barrier_free_app.presentation.home.screen

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.moduro.barrier_free_app.R
import com.moduro.barrier_free_app.core_ui.component.HomePlaceBox
import com.moduro.barrier_free_app.core_ui.component.HomeWeatherBox
import com.moduro.barrier_free_app.core_ui.theme.Background2
import com.moduro.barrier_free_app.core_ui.theme.LocalbarrierFreeTypographyProvider
import com.moduro.barrier_free_app.core_ui.theme.ProvideScaledTypography
import com.moduro.barrier_free_app.core_ui.theme.Text4
import com.moduro.barrier_free_app.presentation.home.navigation.HomeNavigator


@Composable
fun HomeRoute(
    navigator: HomeNavigator
) {
    val systemUiController = rememberSystemUiController()
    val homeViewModel: HomeViewModel = hiltViewModel()
    val airKoreaViewModel: AirKoreaViewModel = hiltViewModel()
    val locationViewModel: LocationViewModel = hiltViewModel()
    val locationNameViewModel: LocationNameViewModel = hiltViewModel()

    var isLargeTextMode by remember { mutableStateOf(false) }

    SideEffect {
        systemUiController.setStatusBarColor(
            color = White
        )
    }

    ProvideScaledTypography(isLargeTextMode = isLargeTextMode) {
        HomeScreen(
            homeViewModel = homeViewModel,
            airKoreaViewModel = airKoreaViewModel,
            locationViewModel = locationViewModel,
            locationNameViewModel = locationNameViewModel,
            isLargeTextMode = isLargeTextMode,
            onToggleTextMode = { isLargeTextMode = !isLargeTextMode },
            onPlaceClick = { placeId ->
                navigator.navigateToPlaceDetail(placeId)
            }
        )
    }
}

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel,
    airKoreaViewModel: AirKoreaViewModel,
    locationViewModel: LocationViewModel,
    locationNameViewModel: LocationNameViewModel,
    isLargeTextMode: Boolean,
    onToggleTextMode: () -> Unit,
    onPlaceClick: (Int) -> Unit
) {
    val hotPlace = homeViewModel.dummyHotPlace
    val weatherPlace = homeViewModel.dummyWeatherPlaces

    val context = LocalContext.current

    // 권한 상태 관리
    var hasLocationPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { granted -> hasLocationPermission = granted }
    )

    // 위치 상태 저장
    var location by remember { mutableStateOf<Location?>(null) }

    // 권한 요청 및 위치 가져오기
    LaunchedEffect(hasLocationPermission) {
        if (!hasLocationPermission) {
            permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            Log.d("HomeScreen", "hasLocationPermission 취소")
        } else {
            val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

            fusedLocationClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, null)
                .addOnSuccessListener { location ->
                    val lat = 37.5  // 테스트용 서울 위도
                    val lon = 127.0 // 테스트용 서울 경도

                    Log.d("HomeScreen", "현재 위치 latitude: $lat, longitude: $lon (테스트용 서울 좌표 고정)")

                    locationViewModel.fetchTemperature(lat, lon)
                    locationNameViewModel.getLocationName(lat, lon)
                }
        }
    }

    //순서대로 현재 기온, 1시간 강수량, 하늘상태
    val temperature by locationViewModel.temperature.collectAsState()
    val rain by locationViewModel.rain.collectAsState()
    val sky by locationViewModel.sky.collectAsState()

    var weathertype = 1

    if (rain != "강수없음") {
        weathertype = 3
    } else {
        if (sky != "1") {
            weathertype = 2
        }
    }



    val locationError by locationViewModel.error.collectAsState()

    val locationName by locationNameViewModel.locationName.collectAsState()

    LaunchedEffect(Unit) {
        airKoreaViewModel.fetchPm10Average()
    }


    val pm10Average by airKoreaViewModel.pm10Average.collectAsState()
    val pm10Grade by airKoreaViewModel.pm10Grade.collectAsState()
    val error by airKoreaViewModel.error.collectAsState()


    // 상태값 변화 로그
    LaunchedEffect(pm10Average) {
        Log.d("HomeScreen", "pm10Average 상태 변화: $pm10Average")
    }

    LaunchedEffect(pm10Grade) {
        Log.d("HomeScreen", "pm10Grade 상태 변화: $pm10Grade")
    }

    val typography = LocalbarrierFreeTypographyProvider.current

    // 바텀시트 보여짐 여부 상태
    var showFilterSheet by remember { mutableStateOf(false) }

    // 바텀시트가 true면 바텀시트 컴포저블 띄우기
    if (showFilterSheet) {
        HomeBottomSheet(
            showSheet = showFilterSheet,
            onDismiss = { showFilterSheet = false },
            onConfirm = { single: String, multi: List<String> ->
                showFilterSheet = false
                println("선택된 알고리즘: $single, 선택된 카테고리: $multi")
            }
        )
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Background2)
            .padding(horizontal = 15.dp)
            .padding(top = 17.dp)

    ) {

        item {
            Row() {
                Image(
                    painter = painterResource(id = R.drawable.moduro_logo),
                    contentDescription = "",
                )

                Spacer(modifier = Modifier.weight(1f))

                Row(
                ) {
                    val toggleIconRes = if (isLargeTextMode) {
                        R.drawable.large_word_selected  // 큰 글자 모드 ON
                    } else {
                        R.drawable.large_word_selected  // 큰 글자 모드 OFF
                    }

                    Image(
                        painter = painterResource(id = toggleIconRes),
                        contentDescription = "",
                        modifier = Modifier.clickable { onToggleTextMode() }
                    )

                    Spacer(modifier = Modifier.width(6.dp))

                    Text("큰 글자 모드", style = typography.H4_SB, color = Text4)
                }

            }

            Spacer(modifier = Modifier.height(20.dp))

            HomeWeatherBox(weathertype, pm10Grade, locationName, temperature, isLargeTextMode)

            Spacer(modifier = Modifier.height(30.dp))



            Column(
            ) {
                Row() {
                    Text("추천 플레이스", style = typography.H4_SB, color = Text4)

                    Spacer(modifier = Modifier.weight(1f))

                    Image(
                        painter = painterResource(id = R.drawable.home_filter),
                        contentDescription = "",
                        modifier = Modifier
                            .height(22.dp)
                            .width(22.dp)
                            .clickable { showFilterSheet = true } // 클릭 시 바텀시트 보여주기
                    )

                }

                Spacer(modifier = Modifier.height(13.dp))

                Text(
                    text = buildAnnotatedString {
                        withStyle(style = typography.H9_B.toSpanStyle().copy(color = Text4)) {
                            append("HOT ")
                        }
                        append("인기 플레이스를 추천해 드릴게요.")
                    },
                    style = typography.H9_M, color = Text4
                )

                Spacer(modifier = Modifier.height(11.dp))

                HomePlaceBox(place = hotPlace) { placeId ->
                    onPlaceClick(placeId)
                }

                Spacer(modifier = Modifier.height(29.dp))

                Text(
                    text = buildAnnotatedString {
                        withStyle(style = typography.H9_B.toSpanStyle().copy(color = Text4)) {
                            append("오늘 날씨에 어울리는 ")
                        }
                        append("장소를 추천해 드릴게요.")
                    },
                    style = typography.H9_M, color = Text4
                )

                Spacer(modifier = Modifier.height(10.dp))

            }
        }

        items(weatherPlace) { place ->
            HomePlaceBox(place = place) { placeId ->
                onPlaceClick(placeId)
            }
            Spacer(modifier = Modifier.height(10.dp))
        }

    }


}


@Composable
@Preview
fun HomeScreenPreview() {
    //val dummyViewModel = HomeViewModel()
    //val dummyViewModel2 9= AirKoreaViewModel()

    //HomeScreen(dummyViewModel, dummyViewModel2,false, {}, {})
}