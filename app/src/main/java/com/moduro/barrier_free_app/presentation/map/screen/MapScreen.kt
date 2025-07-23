package com.moduro.barrier_free_app.presentation.map.screen

import android.graphics.Color
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.moduro.barrier_free_app.R
import com.moduro.barrier_free_app.core_ui.theme.Background1
import com.moduro.barrier_free_app.core_ui.theme.LocalbarrierFreeTypographyProvider
import com.moduro.barrier_free_app.core_ui.theme.MainYellow
import com.moduro.barrier_free_app.domain.entity.MapPlaceEntity
import com.moduro.barrier_free_app.presentation.map.navigation.MapNavigator
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraAnimation
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.clustering.Clusterer
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
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import com.naver.maps.map.clustering.DefaultClusterOnClickListener
import com.naver.maps.map.clustering.DefaultDistanceStrategy
import com.naver.maps.map.util.MarkerIcons

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
        onSearchClick = { navigator.navigateToSearch() }
    )
}

@OptIn(ExperimentalNaverMapApi::class, ExperimentalMaterial3Api::class)
@Composable
fun MapScreen(
    mapViewModel: MapViewModel = hiltViewModel(),
    onDetailClick: (Int) -> Unit,
    onSearchClick: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val typography = LocalbarrierFreeTypographyProvider.current
    val context = LocalContext.current

    val placeList by mapViewModel.placeList.collectAsState()
    val placeDetail by mapViewModel.placeDetail.collectAsState()

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
    }?.gu

    val guPlaces = placeList.filter { it.gu == currentGu }
    val facilityList = guPlaces.flatMap { it.facilities }.distinct()

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
                    // 필터 선택 처리 (필요하면 여기에 구현)
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
            onSearchClick = { onSearchClick() },
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
                                override fun getDistance(zoom: Int, node1: Node, node2: Node): Double {
                                    return if (zoom <= 9) {
                                        -1.0
                                    } else if ((node1.tag as MapPlaceEntity).gu == (node2.tag as MapPlaceEntity).gu) {
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
                                        type = first.type,
                                        gu = first.gu,
                                        facilities = emptyList(),
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
                                        val bitmap = createClusterBitmap(context, size, tag.gu)
                                        marker.icon = OverlayImage.fromBitmap(bitmap)
                                        val density = context.resources.displayMetrics.density
                                        marker.width = (86 * density).toInt()
                                        marker.height = (34 * density).toInt()
                                    }
                                    marker.captionText = ""
                                    marker.subCaptionText = ""
                                } else {
                                    // 일반 클러스터 - NaverMap 기본 원형
                                    marker.icon = when {
                                        size < 10 -> MarkerIcons.CLUSTER_LOW_DENSITY
                                        else -> MarkerIcons.CLUSTER_MEDIUM_DENSITY
                                    }
                                    marker.captionText = size.toString()
                                    marker.subCaptionText = ""
                                    marker.setCaptionAligns(Align.Center)
                                    marker.captionColor = Color.WHITE
                                }

                                marker.anchor = Marker.DEFAULT_ANCHOR
                                marker.onClickListener = DefaultClusterOnClickListener(info)
                            }
                            .leafMarkerUpdater { info, marker ->
                                val tag = info.tag as MapPlaceEntity

                                marker.icon = when (tag.type) {
                                    1 -> OverlayImage.fromResource(R.drawable.ic_map_marker)
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
                                        mapViewModel.loadPlaceDetail(tag.id)
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

            // 현재 구 표시 UI (줌 레벨 조건 있음)
            if (currentGu != null && zoomLevel > 14) {
                Surface(
                    modifier = Modifier
                        .padding(top = 18.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(Black)
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    color = Black
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text("${guPlaces.size}", style = typography.H5_M_10, color = MainYellow)
                        Text("$currentGu", style = typography.H5_M_10, color = Background1)
                        Text("이미지", style = typography.H5_M_10, color = Background1)
                    }
                }
            }
            val place = selectedPlace
            // 장소 상세 바텀시트 (중복 제거)
            if (showPlaceSheet && placeDetail != null && place != null) {
                val data = placeDetail!!
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
                    MapDetailComponent(
                        place = data,
                        distance = "3.8", //distance 재는 로직 업데이트 필요
                        facilities = place.facilities,
                        onClick = { onDetailClick(data.id) }
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