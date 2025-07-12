package com.moduro.barrier_free_app.presentation.map.screen

import androidx.lifecycle.ViewModel
import com.moduro.barrier_free_app.domain.entity.MapPlaceEntity
import com.moduro.barrier_free_app.domain.entity.MapPlaceSummEntity
import com.moduro.barrier_free_app.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor() : ViewModel() {



    // 전체 장소 리스트 (마커용)
    private val _placeList = MutableStateFlow<List<MapPlaceEntity>>(emptyList())
    val placeList: StateFlow<List<MapPlaceEntity>> = _placeList

    // 상세 정보 (마커 클릭 후)
    private val _placeDetail = MutableStateFlow<MapPlaceSummEntity?>(null)
    val placeDetail: StateFlow<MapPlaceSummEntity?> = _placeDetail

    init {
        // 더미 마커 데이터 세팅
        _placeList.value = listOf(
            MapPlaceEntity(1, "용산구", listOf("영유아 동반", "승강기"), 37.5374729285246, 126.965664771338),
            MapPlaceEntity(2, "송파구", listOf("장애인 화장실", "수유실"), 37.5054870414361, 127.083121066144),
            MapPlaceEntity(3, "용산구", listOf("경사로", "장애인 화장실", "수유실", "영유아 동반"), 37.5429793370245, 126.971946055378),
            MapPlaceEntity(4, "용산구", listOf("장애인 화장실", "수유실"), 37.5425431587763, 126.973487034748),
            MapPlaceEntity(5, "마포구", listOf("경사로", "장애인 화장실", "수유실", "영유아 동반"),37.5456041859389, 126.955194223342),
            MapPlaceEntity(6, "관악구", listOf("장애인 화장실", "수유실"), 37.475339578085, 126.981089942431),
            MapPlaceEntity(7, "마포구", listOf("경사로", "장애인 화장실", "수유실", "영유아 동반"), 37.5549789202685, 126.930036332426),
            MapPlaceEntity(8, "관악구",  listOf("장애인 화장실", "수유실"), 37.474629606862, 126.952599252174),
            MapPlaceEntity(9, "강서구", listOf("경사로", "장애인 화장실", "수유실", "영유아 동반"), 37.555567479084, 126.854371723846),
            MapPlaceEntity(10, "강서구", listOf("장애인 화장실", "수유실"), 37.555567479085, 126.854371723847),
            MapPlaceEntity(11, "송파구", listOf("경사로", "장애인 화장실", "수유실", "영유아 동반"), 337.5054870414361, 127.083121066144),
        )
    }

    fun loadPlaceDetail(id: Long) {
        // 더미 상세 정보
        _placeDetail.value = when (id) {
            1L -> MapPlaceSummEntity(1, "국립현대미술관", "서울 종로구 삼청로 30", 1, )
            2L -> MapPlaceSummEntity(2, "서울아산병원", "서울 송파구", 2,)
            3L -> MapPlaceSummEntity(3, "숙명여자대학교", "서울 용산구 청파로", 3, )
            4L -> MapPlaceSummEntity(4, "국립현대미술관", "서울 종로구 삼청로 30", 4, )
            5L -> MapPlaceSummEntity(5, "서울아산병원", "서울 송파구", 5, )
            6L -> MapPlaceSummEntity(6, "숙명여자대학교", "서울 용산구 청파로", 6, )
            7L -> MapPlaceSummEntity(7, "국립현대미술관", "서울 종로구 삼청로 30", 1, )
            8L -> MapPlaceSummEntity(8, "서울아산병원", "서울 송파구", 2, )
            9L -> MapPlaceSummEntity(9, "숙명여자대학교", "서울 용산구 청파로", 3, )
            10L -> MapPlaceSummEntity(10, "국립현대미술관", "서울 종로구 삼청로 30", 4,)
            11L -> MapPlaceSummEntity(11, "서울아산병원", "서울 송파구", 5, )
            else -> null
        }
    }
}