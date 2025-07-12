package com.moduro.barrier_free_app.presentation.home.screen

import kotlin.math.*

data class GridXY(val x: Int, val y: Int)

fun convertToGrid(lat: Double, lon: Double): GridXY {
    val RE = 6371.00877     // 지구 반경(km)
    val GRID = 5.0          // 격자 간격(km)
    val SLAT1 = 30.0        // 투영 위도1(degree)
    val SLAT2 = 60.0        // 투영 위도2(degree)
    val OLON = 126.0        // 기준점 경도(degree)
    val OLAT = 38.0         // 기준점 위도(degree)
    val XO = 43.0           // 기준점 X좌표(GRID)
    val YO = 136.0          // 기준점 Y좌표(GRID)

    val DEGRAD = Math.PI / 180.0
    val re = RE / GRID
    val slat1 = SLAT1 * DEGRAD
    val slat2 = SLAT2 * DEGRAD
    val olon = OLON * DEGRAD
    val olat = OLAT * DEGRAD

    val sn = ln(tan(Math.PI * 0.25 + slat2 * 0.5) / tan(Math.PI * 0.25 + slat1 * 0.5)) /
            ln(cos(slat1) / cos(slat2))

    val sf = (tan(Math.PI * 0.25 + slat1 * 0.5)).pow(sn) * cos(slat1) / sn
    val ro = re * sf / (tan(Math.PI * 0.25 + olat * 0.5)).pow(sn)

    val ra = re * sf / (tan(Math.PI * 0.25 + lat * DEGRAD * 0.5)).pow(sn)
    var theta = lon * DEGRAD - olon

    if (theta > Math.PI) theta -= 2.0 * Math.PI
    if (theta < -Math.PI) theta += 2.0 * Math.PI
    theta *= sn

    val x = (ra * sin(theta) + XO + 0.5).toInt()
    val y = ((ro - ra * cos(theta)) + YO + 0.5).toInt()

    return GridXY(x, y)
}
