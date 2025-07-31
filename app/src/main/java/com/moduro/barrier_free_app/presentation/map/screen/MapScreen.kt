package com.moduro.barrier_free_app.presentation.map.screen

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.moduro.barrier_free_app.R
import com.moduro.barrier_free_app.core_ui.theme.LocalbarrierFreeTypographyProvider
import com.moduro.barrier_free_app.domain.entity.MapPlaceEntity
import com.moduro.barrier_free_app.presentation.home.screen.LocationViewModel
import com.moduro.barrier_free_app.presentation.map.navigation.MapNavigator
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraAnimation
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.clustering.Clusterer
import com.naver.maps.map.clustering.DefaultClusterOnClickListener
import com.naver.maps.map.clustering.DefaultDistanceStrategy
import com.naver.maps.map.clustering.DefaultMarkerManager
import com.naver.maps.map.clustering.DistanceStrategy
import com.naver.maps.map.clustering.Node
import com.naver.maps.map.compose.DisposableMapEffect
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.LocationTrackingMode
import com.naver.maps.map.compose.MapProperties
import com.naver.maps.map.compose.MapUiSettings
import com.naver.maps.map.compose.NaverMap
import com.naver.maps.map.compose.rememberCameraPositionState
import com.naver.maps.map.overlay.Align
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.Overlay
import com.naver.maps.map.overlay.OverlayImage
import kotlinx.coroutines.launch
import kotlin.math.abs

@Composable
fun MapRoute(
    navigator: MapNavigator
) {
    val mapViewModel: MapViewModel = hiltViewModel()
    val systemUiController = rememberSystemUiController()


    SideEffect {
        systemUiController.setStatusBarColor(
            color = White,
        )
    }

    MapScreen(
        mapViewModel = mapViewModel,
        onDetailClick = { id -> navigator.navigateToPlaceDetail(id.toInt()) },
        onSearchClick = { searchValue -> navigator.navigateToSearch(searchValue) }
    )
}

@OptIn(ExperimentalNaverMapApi::class, ExperimentalMaterial3Api::class)
@Composable
fun MapScreen(
    mapViewModel: MapViewModel,
    onDetailClick: (Long) -> Unit,
    onSearchClick: (String?) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val typography = LocalbarrierFreeTypographyProvider.current
    val context = LocalContext.current


    val placeList by mapViewModel.mapPlaceList.observeAsState(emptyList())
    val placeSumm by mapViewModel.mapPlaceSumm.observeAsState()

    var showFilterSheet by remember { mutableStateOf(false) }
    var selectedPlace by remember { mutableStateOf<MapPlaceEntity?>(null) }
    var showPlaceSheet by remember { mutableStateOf(false) }
    var search by remember { mutableStateOf("") }

    val sheetStateFilter = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition(LatLng(37.541, 126.986), 11.0)
    }

    val zoomLevel = cameraPositionState.position.zoom
    val cameraCenter = cameraPositionState.position.target
    val currentGu = placeList.find {
        abs(it.latitude - cameraCenter.latitude) < 0.01 &&
                abs(it.longitude - cameraCenter.longitude) < 0.01
    }?.region

    val guPlaces = placeList.filter { it.region == currentGu }

    LaunchedEffect(Unit) {
        mapViewModel.getMapPlaces()
    }


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

                    Log.d("MapScreen", "현재 위치 latitude: $lat, longitude: $lon (테스트용 서울 좌표 고정)")

                }
        }
    }


    // UI: 위치 값이 있으면 출력
    location?.let {
        Log.e("MapScreen","현재 위치: 위도 ${it.latitude}, 경도 ${it.longitude}")
    } ?: run {
    }



    // 필터용 바텀시트
    if (showFilterSheet) {
        ModalBottomSheet(
            onDismissRequest = { showFilterSheet = false },
            sheetState = sheetStateFilter,
            modifier = Modifier.fillMaxWidth(),
            containerColor = White
        ) {
            MapFilterContent(
                onDismiss = { showFilterSheet = false },
                onConfirm = { multi ->
                    showFilterSheet = false
                    val selected = if (multi == listOf(0)){
                        listOf(1,2,3,4,5)
                    } else multi

                    mapViewModel.getMapPlaces(
                        facilities = if (selected.isEmpty()) null else selected
                    )
                }
            )
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 20.dp)
    ) {
        MapScreenTop(
            search = search,
            onSearchChange = { search = it },
            onSearchClick = { onSearchClick(search) },
            onFilterClick = {
                coroutineScope.launch { showFilterSheet = true }
            }
        )

        Box(modifier = Modifier.fillMaxSize()) {

            var clusterManager: Clusterer<ItemKey>? = null

            NaverMap(
                cameraPositionState = cameraPositionState,
                properties = MapProperties(
                    locationTrackingMode = LocationTrackingMode.Follow,
                    maxZoom = 18.0,
                    minZoom = 5.0
                ),
                uiSettings = MapUiSettings(isLocationButtonEnabled = true),
                onMapClick = { _, _ -> showPlaceSheet = false }
            ) {
                var clusterManager by remember { mutableStateOf<Clusterer<ItemKey>?>(null) }

                DisposableMapEffect(placeList) { map ->
                    if (clusterManager == null) {
                        clusterManager = Clusterer.ComplexBuilder<ItemKey>()
                            .minClusteringZoom(9)
                            .maxClusteringZoom(16)
                            .maxScreenDistance(200.0)
                            .thresholdStrategy { zoom ->
                                if (zoom <= 11) {
                                    0.0
                                } else {
                                    70.0
                                }
                            }
                            .distanceStrategy(object : DistanceStrategy {
                                private val defaultDistanceStrategy = DefaultDistanceStrategy()
                                override fun getDistance(
                                    zoom: Int,
                                    node1: Node,
                                    node2: Node
                                ): Double {
                                    return if (zoom <= 9) {
                                        -1.0
                                    } else if ((node1.tag as MapPlaceEntity).region == (node2.tag as MapPlaceEntity).region) {
                                        if (zoom <= 11) {
                                            -1.0
                                        } else {
                                            defaultDistanceStrategy.getDistance(zoom, node1, node2)
                                        }
                                    } else {
                                        10000.0
                                    }
                                }
                            })
                            .tagMergeStrategy { cluster ->
                                if (cluster.maxZoom <= 9) {
                                    null
                                } else {
                                    val first = cluster.children.first().tag as MapPlaceEntity
                                    MapPlaceEntity(
                                        id = -1L,
                                        name = "",
                                        placeType = first.placeType,
                                        region = first.region,
                                        latitude = first.latitude,
                                        longitude = first.longitude
                                    )
                                }
                            }
                            .markerManager(object : DefaultMarkerManager() {
                                override fun createMarker() = super.createMarker().apply {
                                    subCaptionTextSize = 10f
                                    subCaptionColor = Color.WHITE
                                    captionTextSize = 12f
                                    captionColor = Color.WHITE
                                }
                            })
                            .clusterMarkerUpdater { info, marker ->
                                val size = info.size

                                if (info.minZoom <= 10) {
                                    // 구 단위 클러스터 - 커스텀 가로형 마커
                                    val tag = info.tag as? MapPlaceEntity
                                    if (tag != null) {
                                        val bitmap = createClusterBitmap(context, size, tag.region)
                                        marker.icon = OverlayImage.fromBitmap(bitmap)
                                        val density = context.resources.displayMetrics.density
                                        marker.width = (86 * density).toInt()
                                        marker.height = (34 * density).toInt()
                                    }
                                    marker.captionText = ""
                                    marker.subCaptionText = ""
                                } else {
                                    val bitmap = createCircleClusterBitmap(context, size)
                                    marker.icon = OverlayImage.fromBitmap(bitmap)
                                    val density = context.resources.displayMetrics.density
                                    val diameter = (40 * density).toInt()  // 고정 크기 (원형)
                                    marker.width = diameter
                                    marker.height = diameter

                                    marker.captionText = ""
                                    marker.subCaptionText = ""
                                }

                                marker.anchor = Marker.DEFAULT_ANCHOR
                                marker.onClickListener = DefaultClusterOnClickListener(info)
                            }
                            .leafMarkerUpdater { info, marker ->
                                val tag = info.tag as MapPlaceEntity

                                marker.icon = when (tag.placeType) {
                                    "map" -> OverlayImage.fromResource(R.drawable.ic_map_marker)
                                    else -> OverlayImage.fromResource(R.drawable.ic_map_marker_yellow)
                                }

                                val density = context.resources.displayMetrics.density
                                marker.width = (31 * density).toInt()
                                marker.height = (45 * density).toInt()
                                marker.anchor = Marker.DEFAULT_ANCHOR

                                marker.captionText = tag.name
                                marker.setCaptionAligns(Align.Bottom)
                                marker.captionColor = Color.BLACK
                                marker.captionTextSize = 12f
                                marker.subCaptionText = ""

                                marker.onClickListener = Overlay.OnClickListener { _ ->
                                    val position = marker.position
                                    val cameraUpdate = CameraUpdate
                                        .scrollAndZoomTo(position, 18.0)
                                        .animate(CameraAnimation.Easing)
                                    map.moveCamera(cameraUpdate)

                                    if (selectedPlace?.id != tag.id) {
                                        mapViewModel.getMapPlaceSumm(
                                            (tag.id).toInt(),
                                            tag.placeType
                                        )
                                        selectedPlace = tag
                                    }
                                    showPlaceSheet = true
                                    true
                                }
                            }
                            .build()
                            .apply { this.map = map }
                    }

                    // 장소 데이터를 클러스터에 추가
                    val keyTagMap = placeList.associate {
                        ItemKey(it.id.toInt(), LatLng(it.latitude, it.longitude)) to it
                    }
                    clusterManager?.addAll(keyTagMap)

                    onDispose {
                        clusterManager?.clear()
                    }
                }
            }

            val place = selectedPlace
            var isHeartClicked by remember(placeSumm) {
                mutableStateOf(placeSumm?.favorite == true)
            }

            // 장소 상세 바텀시트 (중복 제거)
            if (showPlaceSheet && placeSumm != null && place != null) {
                val data = placeSumm!!

                Surface(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(16.dp)
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .shadow(8.dp, RoundedCornerShape(16.dp)),
                    color = White,
                    shape = RoundedCornerShape(16.dp)
                ) {
                    val userLat = location?.latitude
                    val userLon = location?.longitude

                    val placeLat = place.latitude
                    val placeLon = place.longitude

                    val distance = if (userLat != null && userLon != null && placeLat != null && placeLon != null) {
                        val d = calculateDistanceInKm(userLat, userLon, 37.5 , 127.0) //테스트용, placeLat이랑 placeLon으로 수정
                        String.format("%.1f", d) // 소수점 1자리까지 포맷 (예: "3.8")
                    } else {
                        ""
                    }

                    MapDetailComponent(
                        place = data,
                        distance = distance,
                        facilities = data.facilities,
                        onClick = { onDetailClick(place.id) },
                        isHeartClicked = isHeartClicked,
                        onHeartClickChanged = { newValue ->
                            isHeartClicked = newValue
                            mapViewModel.postMapLike(
                                placeId = place.id,
                                type = place.placeType
                            )
                        }
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun MapScreenPreview() {
    MapScreen(
        mapViewModel = hiltViewModel(),
        onDetailClick = { },
        onSearchClick = {}
    )
}