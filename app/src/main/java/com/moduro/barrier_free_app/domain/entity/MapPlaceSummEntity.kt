package com.moduro.barrier_free_app.domain.entity

data class MapPlaceSummEntity (
    val name: String,
    val description : String,
    val address: String,
    val facilities : List<Int>,
    val placeType : String,
    val imageType : Int,
    val favorite : Boolean,
    )