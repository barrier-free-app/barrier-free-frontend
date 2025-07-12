package com.moduro.barrier_free_app.presentation.home.screen

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

suspend fun FusedLocationProviderClient.awaitLocation(context: android.content.Context): Location? =
    suspendCancellableCoroutine { cont ->
        // 권한 체크
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            cont.resume(null)
            return@suspendCancellableCoroutine
        }

        lastLocation
            .addOnSuccessListener { location ->
                cont.resume(location)
            }
            .addOnFailureListener {
                cont.resume(null)
            }
    }
