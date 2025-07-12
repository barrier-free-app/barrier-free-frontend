package com.moduro.barrier_free_app.presentation.map.screen

import androidx.compose.foundation.Image
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.moduro.barrier_free_app.presentation.map.navigation.MapNavigator
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.moduro.barrier_free_app.R
import com.moduro.barrier_free_app.core_ui.theme.Background2
import com.moduro.barrier_free_app.domain.entity.MapPlaceEntity
import com.moduro.barrier_free_app.util.UiState
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraAnimation
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.clustering.Clusterer
import com.naver.maps.map.clustering.DefaultClusterMarkerUpdater
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
import com.naver.maps.map.compose.NaverMapConstants
import com.naver.maps.map.compose.rememberCameraPositionState
import com.naver.maps.map.overlay.Align
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.Overlay
import com.naver.maps.map.overlay.OverlayImage
import com.naver.maps.map.util.MarkerIcons
import timber.log.Timber
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
        onDetailClick = { id -> navigator.navigateToPlaceDetail(id.toInt()) }
    )
}

@OptIn(ExperimentalNaverMapApi::class)
@Composable
fun MapScreen(
    mapViewModel: MapViewModel = hiltViewModel(),
    onDetailClick: (Long) -> Unit
) {
    val placeList by mapViewModel.placeList.collectAsState()
    val placeDetail by mapViewModel.placeDetail.collectAsState()
    val context = LocalContext.current

    var search by remember { mutableStateOf("") }

    var showDetail by remember { mutableStateOf(false) }
    var selectedPlace by remember { mutableStateOf<MapPlaceEntity?>(null) }

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



    var clusterManager by remember { mutableStateOf<Clusterer<ItemKey>?>(null) }

    Column(modifier = Modifier.fillMaxSize().padding(top = 20.dp)) {

        MapScreenTop(
            search = search,
            onSearchChange = { newSearch -> search = newSearch },
            onSearchClick = {}
        )


        Box(modifier = Modifier.fillMaxSize()) {
            NaverMap(
                cameraPositionState = cameraPositionState,
                properties = MapProperties(
                    locationTrackingMode = LocationTrackingMode.Follow,
                    maxZoom = 18.0,
                    minZoom = 5.0
                ),
                uiSettings = MapUiSettings(isLocationButtonEnabled = true),
                onMapClick = { _, _ -> showDetail = false }
            ) {
                DisposableMapEffect(placeList) { map ->
                    if (clusterManager == null) {
                        clusterManager = Clusterer.ComplexBuilder<ItemKey>()
                            .minClusteringZoom(9)
                            .maxClusteringZoom(16)
                            .thresholdStrategy { zoom -> if (zoom <= 11) 0.0 else 70.0 }
                            .distanceStrategy(object : DistanceStrategy {
                                private val defaultStrategy = DefaultDistanceStrategy()

                                override fun getDistance(
                                    zoom: Int,
                                    node1: Node,
                                    node2: Node
                                ): Double {
                                    val tag1 = node1.tag as MapPlaceEntity
                                    val tag2 = node2.tag as MapPlaceEntity

                                    return if (zoom <= 9 && tag1.gu == tag2.gu) {
                                        defaultStrategy.getDistance(zoom, node1, node2)
                                    } else if (zoom <= 9) {
                                        10000.0 // 서로 다른 구는 묶지 않음
                                    } else {
                                        -1.0 // 클러스터링 안함
                                    }
                                }
                            })
                            .tagMergeStrategy { cluster ->
                                val first = cluster.children.first().tag as MapPlaceEntity
                                MapPlaceEntity(
                                    id = -1L,
                                    gu = first.gu,
                                    facilities = emptyList(),
                                    latitude = first.latitude,
                                    longitude = first.longitude
                                )
                            }
                            .markerManager(object : DefaultMarkerManager() {
                                override fun createMarker(): Marker {
                                    return super.createMarker().apply {
                                        subCaptionTextSize = 10f
                                        subCaptionColor = White.toArgb()
                                        subCaptionHaloColor = Transparent.toArgb()
                                    }
                                }
                            })
                            .clusterMarkerUpdater { info, marker ->
                                val tag = info.tag as MapPlaceEntity
                                marker.icon = MarkerIcons.CLUSTER_MEDIUM_DENSITY
                                marker.captionText = "${info.size}"
                                marker.subCaptionText = tag.gu
                                marker.setCaptionAligns(Align.Center)
                                marker.anchor = Marker.DEFAULT_ANCHOR
                            }
                            .leafMarkerUpdater { info, marker ->
                                val tag = info.tag as MapPlaceEntity
                                marker.icon = OverlayImage.fromResource(R.drawable.ic_map_marker)
                                marker.anchor = Marker.DEFAULT_ANCHOR
                                marker.captionText = tag.gu
                                marker.setCaptionAligns(Align.Bottom)
                                marker.onClickListener = Overlay.OnClickListener {
                                    map.moveCamera(
                                        CameraUpdate.scrollAndZoomTo(
                                            LatLng(tag.latitude, tag.longitude),
                                            18.0
                                        ).animate(CameraAnimation.Easing)
                                    )
                                    mapViewModel.loadPlaceDetail(tag.id)
                                    selectedPlace = tag
                                    showDetail = true
                                    true
                                }
                            }
                            .build()
                            .apply { this.map = map }
                    }

                    val keyTagMap = placeList.associate {
                        ItemKey(it.id.toInt(), LatLng(it.latitude, it.longitude)) to it
                    }

                    clusterManager?.addAll(keyTagMap)

                    onDispose {
                        clusterManager?.clear()
                    }
                }
            }

            if (currentGu != null && zoomLevel > 14) {  // 줌 레벨 조건 넣는 것도 추천
                Surface(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(top = 100.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(Black)
                        .padding(horizontal = 20.dp, vertical = 12.dp),
                    shadowElevation = 4.dp
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("$currentGu (${guPlaces.size}개)", )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(facilityList.joinToString(", "))
                    }
                }
            }

            if (showDetail && placeDetail != null && selectedPlace != null) {
                val data = placeDetail!!
                Surface(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(16.dp)
                        .clickable { onDetailClick(data.id) }
                        .shadow(4.dp, RoundedCornerShape(16.dp)),
                    color = White,
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = data.name)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = data.address)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = "유형 코드: ${data.type}")
                    }
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
        onDetailClick = { }
    )
}