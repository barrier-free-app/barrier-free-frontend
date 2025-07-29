package com.moduro.barrier_free_app.presentation.map.screen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moduro.barrier_free_app.domain.entity.HomePlaceEntity
import com.moduro.barrier_free_app.domain.entity.MapPlaceEntity
import com.moduro.barrier_free_app.domain.entity.MapPlaceSummEntity
import com.moduro.barrier_free_app.domain.repository.MapRepository
import com.moduro.barrier_free_app.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val mapRepository: MapRepository
) : ViewModel() {

    private val _mapPlaceList = MutableLiveData<List<MapPlaceEntity>>()
    val mapPlaceList: LiveData<List<MapPlaceEntity>> = _mapPlaceList

    private val _mapPlaceSumm = MutableLiveData<MapPlaceSummEntity>()
    val mapPlaceSumm: LiveData<MapPlaceSummEntity> = _mapPlaceSumm

    private val _isLike = MutableLiveData<Boolean>()
    val isLike : MutableLiveData<Boolean> = _isLike


    fun getMapPlaces() {
        viewModelScope.launch {
            val result = mapRepository.getMapPlaces()

            result.onSuccess { places ->
                _mapPlaceList.value = places

            }.onFailure { exception ->
                Log.e("MapViewModel - getMapPlaces", "Fail", exception)
            }
        }
    }


    fun getMapPlaceSumm(placeId : Int, placeType : String) {
        viewModelScope.launch {
            val result = mapRepository.getMapPlaceSumm(placeId, placeType)

            result.onSuccess { placeSumm ->
                _mapPlaceSumm.value = placeSumm

            }.onFailure { exception ->
                Log.e("MapViewModel - getMapPlaceSumm", "Fail", exception)
            }
        }
    }

    fun postMapLike(placeId : Long, type : String){
        viewModelScope.launch {
            val result = mapRepository.postMapLike(placeId, type)

            result.onSuccess { like ->
                _isLike.value = like
            }.onFailure { exception ->
                Log.e("MapViewModel - postMapLike", "Fail", exception )
            }
        }
    }
}