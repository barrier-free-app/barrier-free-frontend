package com.moduro.barrier_free_app.presentation.mypage.screen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moduro.barrier_free_app.domain.entity.MapPlaceEntity
import com.moduro.barrier_free_app.domain.entity.MapPlaceSummEntity
import com.moduro.barrier_free_app.domain.repository.MapRepository
import com.moduro.barrier_free_app.domain.repository.PlaceReportRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.serialization.json.JsonElement
import javax.inject.Inject


@HiltViewModel
class PlaceReportViewModel @Inject constructor(
    private val placeReportRepository : PlaceReportRepository
) : ViewModel() {

    fun postMapLike(
        description : String,
        name : String,
        address : String,
        imageType : Int,
        facilities : List<Int>,
        homepage : String?,
        openingHours : String?,
        contact : String?
    ){
        viewModelScope.launch {
            val result = placeReportRepository.postPlaceReport(
                description = description,
                name = name,
                address = address,
                imageType = imageType,
                facilities = facilities,
                homepage = homepage,
                openingHours = openingHours,
                contact = contact
            )

            result.onSuccess {
                Log.e("PlaceReportViewModel", "Success")
            }.onFailure { exception ->
                Log.e("MapViewModel - postMapLike", "Fail", exception )
            }
        }
    }
}